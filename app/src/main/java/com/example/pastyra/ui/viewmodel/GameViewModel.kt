package com.example.pastyra.ui.viewmodel

import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.GameState
import com.example.pastyra.domain.model.Player
import com.example.pastyra.domain.model.Stage
import com.example.pastyra.domain.usercase.activeTableCardEqualsPlayerCard
import com.example.pastyra.domain.usercase.selectArithmeticTableCard
import com.example.pastyra.domain.usercase.selectCardForBundle
import com.example.pastyra.domain.usercase.selectQueenAndKingCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

open class GameViewModel : ViewModel() {
    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    @get:VisibleForTesting
    internal val mutableState: MutableStateFlow<GameState> get() = _state

    var cardForDeleteFromDisplay by mutableStateOf<List<Card>>(emptyList())
        private set

    private var stateSavePoint: GameState? = null

    private val selectionMutex = Mutex()

    init {
        startGame()
    }

    private fun startGame() {
        addPlayers()
        selectFirstPlayer()
        dealInitialCard()
    }

    val activePlayer: Player?
        get() =
            state.value.game.playerList
                .find { it.isActive }

    val requiredActivePlayer: Player
        get() = activePlayer ?: throw AssertionError("No active player")

    val requiredActivePlayerCard: Card
        get() = requiredActivePlayer.activeCard ?: throw AssertionError("No active player card")

    val requireActivePlayerBundleCard: Card.AnyArithmeticCard.ArithmeticCard
        get() =
            requiredActivePlayer.cardToBeBundled
                ?: throw AssertionError("No active player bundle card")

    fun updateActivePlayerCard(card: Card) {
        _state.update { currentState ->
            val newPlayerList =
                currentState.game.playerList.map { player ->
                    if (player.playerId == requiredActivePlayer.playerId) {
                        player.copy(activeCard = card)
                    } else {
                        player
                    }
                }

            currentState.copy(
                game =
                    currentState.game.copy(
                        playerList = newPlayerList,
                    ),
            )
        }
    }

    fun selectCard(card: Card) {
        viewModelScope.launch {
            _state.update { currentState ->

                val newPlayerList =
                    currentState.game.playerList.map { pl ->
                        if (pl.playerId == requiredActivePlayer.playerId && currentState.game.gameStage == Stage.DefaultStage) {
                            pl.copy(
                                activeCard = card,
                            )
                        } else {
                            pl
                        }
                    }

                currentState.copy(
                    game =
                        currentState.game.copy(
                            playerList = newPlayerList,
                        ),
                )
            }
        }
    }

    fun selectTableCard(card: Card) {
        viewModelScope.launch {
            selectionMutex.withLock {
                _state.update { currentState ->
                    val activePlayer = requiredActivePlayer

                    if (activePlayer.activeCard == null || activePlayer.activeCard is Card.JackCard) return@update currentState

                    val newTableActiveCard =
                        when (activePlayer.activeCard) {
                            is Card.AnyArithmeticCard.ArithmeticCard -> {
                                if (currentState.game.gameStage == Stage.TakeArithmeticCard) {
                                    selectArithmeticTableCard(
                                        card,
                                        currentState,
                                        activePlayer,
                                    )
                                } else {
                                    return@update currentState
                                }
                            }

                            is Card.QueenCard, is Card.KingCard -> {
                                if (currentState.game.gameStage == Stage.TakeQueenOrKingCard) {
                                    selectQueenAndKingCard(
                                        card,
                                        currentState,
                                        activePlayer,
                                    )
                                } else {
                                    return@update currentState
                                }
                            }

                            else -> throw IllegalArgumentException("")
                        }

                    val newTableDeck =
                        currentState.game.tableDeck.toMutableList() - newTableActiveCard

                    val newTemporaryPlayerTakenCard =
                        currentState.game.temporaryPlayerTakenCards.toMutableList()

                    if (newTableActiveCard.any { card -> card is Card.AnyArithmeticCard } &&
                        activeTableCardEqualsPlayerCard(
                            activePlayer.activeCard as Card.AnyArithmeticCard.ArithmeticCard,
                            newTableActiveCard,
                        )
                    ) {
                        newTemporaryPlayerTakenCard.addAll(newTableActiveCard)
                        newTableActiveCard.clear()
                    } else if (newTableActiveCard.any { card -> card is Card.KingCard || card is Card.QueenCard }) {
                        newTemporaryPlayerTakenCard.addAll(newTableActiveCard)
                        newTableActiveCard.clear()
                    }

                    currentState.copy(
                        game =
                            currentState.game.copy(
                                tableActiveCards = newTableActiveCard,
                                tableDeck = newTableDeck,
                                temporaryPlayerTakenCards = newTemporaryPlayerTakenCard,
                            ),
                    )
                }
            }
        }
    }

    fun selectTableCardForBundle(card: Card) {
        viewModelScope.launch {
            selectionMutex.withLock {
                _state.update { currentState ->
                    val tableDeck = currentState.game.tableDeck

                    val activePlayer = requiredActivePlayer

                    if (activePlayer.cardToBeBundled == null) return@update currentState

                    val newTableActiveCard =
                        selectCardForBundle(
                            cardToBeBundled = card,
                            tableDeck = currentState.game.tableActiveCards,
                            player = activePlayer,
                        )

                    val newTableDeck = tableDeck - newTableActiveCard

                    val newTempPlayerBundlingCards =
                        currentState.game.temporaryPlayerBundlingCards.toMutableList()

                    if (activeTableCardEqualsPlayerCard(
                            activePlayer.cardToBeBundled,
                            newTableActiveCard,
                        )
                    ) {
                        newTempPlayerBundlingCards.add(newTableActiveCard.toList())
                        newTableActiveCard.clear()
                    }

                    currentState.copy(
                        game =
                            currentState.game.copy(
                                tableActiveCards = newTableActiveCard,
                                tableDeck = newTableDeck,
                                temporaryPlayerBundlingCards = newTempPlayerBundlingCards,
                            ),
                    )
                }
            }
        }
    }

    fun selectCardForDeleteFromDisplay(card: Card) {
        if (cardForDeleteFromDisplay.any { it == card }) {
            cardForDeleteFromDisplay -= card
        } else {
            cardForDeleteFromDisplay += card
        }
    }

    fun confirmDeleteCardFromDisplay() {
        _state.update { currentState ->
            val newTableActiveCards = currentState.game.tableActiveCards.toMutableList()

            newTableActiveCards.removeAll(cardForDeleteFromDisplay)

            val combinedTableDeck = currentState.game.tableDeck + cardForDeleteFromDisplay
            cardForDeleteFromDisplay = emptyList()

            currentState.copy(
                game =
                    currentState.game.copy(
                        tableActiveCards = newTableActiveCards,
                        tableDeck = combinedTableDeck,
                    ),
            )
        }
    }

    fun closeDialogBox() {
        cardForDeleteFromDisplay = emptyList()
    }

    fun playerSelectHisCardInDisplay() {
        cardForDeleteFromDisplay = emptyList()
        cancelMove()
    }

    fun finishMove() {
        Log.d("TEST_JACK", "JACK")
        tryAddPastureScore()
        switchActivePlayer()
        tryDealNewCards()
        removeState()
    }

    fun tryAddPastureScore() {
        _state.update { currentState ->
            val game = currentState.game
            val currentPlayer = requiredActivePlayer
            val oldTableDeck = stateSavePoint?.game?.tableDeck

            val isEmptyGameDeck = game.gameDeck.isEmpty()
            val isEmptyTableDeck = game.tableDeck.isEmpty()
            val isJackTakeJackCard =
                when {
                    currentPlayer.activeCard is Card.JackCard && oldTableDeck?.size == 1 && oldTableDeck[0] is Card.JackCard -> true

                    else -> false
                }

            val newPlayerList =
                game.playerList.map { player ->
                    player.copy(
                        pastyrs =
                            when {
                                currentPlayer.playerId == player.playerId -> player.pastyrs + 1
                                else -> player.pastyrs
                            },
                    )
                }

            if (isJackTakeJackCard || (!isEmptyGameDeck && isEmptyTableDeck)) {
                currentState.copy(
                    game =
                        game.copy(
                            playerList = newPlayerList,
                        ),
                )
            } else {
                currentState
            }
        }
    }

    fun removeState() {
        _state.update { currentState ->

            currentState.copy(
                game =
                    currentState.game.copy(
                        temporaryPlayerTakenCards = emptyList(),
                        temporaryPlayerBundlingCards = emptyList(),
                        tableActiveCards = emptyList(),
                        gameStage = Stage.DefaultStage,
                    ),
            )
        }
    }

    fun tryDealNewCards() {
        if (state.value.game.playerList
                .all { it.cardsHand.isEmpty() }
        ) {
            dealCards()
        }
    }

    fun putCardOnTable() {
        _state.update { currentState ->
            val updatedPlayerList =
                currentState.game.playerList.map { pl ->
                    when {
                        pl.playerId == requiredActivePlayer.playerId ->
                            pl.copy(
                                cardsHand = pl.cardsHand - requiredActivePlayerCard,
                                activeCard = null,
                            )

                        else -> pl
                    }
                }

            val newTableDeck = currentState.game.tableDeck + requiredActivePlayerCard

            currentState.copy(
                game =
                    currentState.game.copy(
                        playerList = updatedPlayerList,
                        tableDeck = newTableDeck,
                    ),
            )
        }
    }

    fun takeCard() {
        when (requiredActivePlayerCard) {
            is Card.AnyArithmeticCard.ArithmeticCard -> {
                takeAnyArithmeticCard()
            }

            is Card.QueenCard, is Card.KingCard -> {
                takeQueenAndKingCard()
            }

            is Card.JackCard -> {
                takeJackCard()
            }

            else -> throw IllegalArgumentException("")
        }
        setLastTakenCardPlayer()
    }

    fun setLastTakenCardPlayer() {
        _state.update { currentState ->
            currentState.copy(
                game =
                    currentState.game.copy(
                        lastTakenCardPlayer = requiredActivePlayer,
                    ),
            )
        }
    }

    fun switchActivePlayer() {
        _state.update { currentState ->
            val game = currentState.game
            val playerList = game.playerList
            val playerOrderIds = game.playerOrderIds

            val newPlayerOrderIds =
                playerOrderIds.subList(1, playerOrderIds.size) + playerOrderIds.first()

            val newPlayerList =
                playerList.map { player ->
                    player.copy(
                        isActive = player.playerId == newPlayerOrderIds.first(),
                        activeCard = null,
                    )
                }

            currentState.copy(
                game =
                    game.copy(
                        playerList = newPlayerList,
                        playerOrderIds = newPlayerOrderIds,
                    ),
            )
        }
    }

    private fun takeAnyArithmeticCard() {
        _state.update { currentState ->
            val newTemporaryPlayerTakenCards =
                currentState.game.temporaryPlayerTakenCards.toMutableList()

            val newPlayerList =
                currentState.game.playerList.map { pl ->
                    var updatedPlayer = pl

                    if (newTemporaryPlayerTakenCards.any { it == pl.bundlingCard }) {
                        val currentPlayerBundling =
                            currentState.game.playerList.find { it.bundlingCard == pl.bundlingCard }
                        when {
                            pl.playerId == currentPlayerBundling?.playerId -> {
                                updatedPlayer =
                                    updatedPlayer.copy(
                                        bundlingCard = null,
                                    )
                            }
                        }
                    }

                    when {
                        pl.playerId == requiredActivePlayer.playerId -> {
                            updatedPlayer =
                                updatedPlayer.copy(
                                    cardsTaken = pl.cardsTaken + newTemporaryPlayerTakenCards + requiredActivePlayerCard,
                                    cardsHand = pl.cardsHand - requiredActivePlayerCard,
                                )
                        }
                    }

                    updatedPlayer
                }

            currentState.copy(
                game =
                    currentState.game.copy(
                        playerList = newPlayerList,
                    ),
            )
        }
    }

    fun takeQueenAndKingCard() {
        _state.update { currentState ->
            val tableTemporaryCard = currentState.game.temporaryPlayerTakenCards.toMutableList()
            val cardToPlay = requiredActivePlayerCard

            val newPLayerList =
                currentState.game.playerList.map { pl ->
                    when {
                        pl.playerId == requiredActivePlayer.playerId -> {
                            pl.copy(
                                cardsTaken = pl.cardsTaken + tableTemporaryCard + cardToPlay,
                                cardsHand = pl.cardsHand - requiredActivePlayerCard,
                            )
                        }

                        else -> {
                            pl
                        }
                    }
                }

            currentState.copy(
                game =
                    currentState.game.copy(
                        playerList = newPLayerList,
                    ),
            )
        }
    }

    fun takeJackCard() {
        _state.update { currentState ->
            val tableDeck = currentState.game.tableDeck.toMutableList()
            val newPlayerTakenCard = requiredActivePlayer.cardsTaken.toMutableList()

            tableDeck.forEach { tableCard ->
                if (tableCard is Card.AnyArithmeticCard.ArithmeticCard || tableCard is Card.JackCard) {
                    newPlayerTakenCard.add(tableCard)
                }
            }

            val newPlayerList =
                currentState.game.playerList.map { pl ->
                    if (pl.playerId == requiredActivePlayer.playerId) {
                        pl.copy(
                            cardsTaken = newPlayerTakenCard + requiredActivePlayerCard,
                            cardsHand = requiredActivePlayer.cardsHand - requiredActivePlayerCard,
                        )
                    } else {
                        pl
                    }
                }

            val newTableDeck = tableDeck - newPlayerTakenCard

            currentState.copy(
                game =
                    currentState.game.copy(
                        playerList = newPlayerList,
                        tableDeck = newTableDeck,
                    ),
            )
        }
    }

    fun bundleCard() {
        _state.update { currentState ->
            val bundlingCardsList = mutableListOf<Card.AnyArithmeticCard.ArithmeticCard>()
            val temporaryCardsList = mutableListOf<Card.AnyArithmeticCard>()

            val playerWhoHadABundle: Player? =
                currentState.game.playerList.firstOrNull { player ->
                    player.bundlingCard in
                        currentState.game.temporaryPlayerBundlingCards
                            .flatten()
                            .filterIsInstance<Card.AnyArithmeticCard.BundleCard>()
                }

            currentState.game.temporaryPlayerBundlingCards.flatten().forEach { card ->
                temporaryCardsList.add(card)
            }

            val temporaryBundleCards = currentState.game.temporaryPlayerBundlingCards
            val playerBundleCard = requireActivePlayerBundleCard

            if (temporaryCardsList.isEmpty()) return@update currentState

            val bundleStage =
                temporaryCardsList.maxOf { card ->
                    if (card is Card.AnyArithmeticCard.BundleCard) {
                        (temporaryBundleCards.size - 1) + card.bundleStage
                    } else {
                        temporaryBundleCards.size
                    }
                }

            currentState.game.temporaryPlayerBundlingCards.forEach { cards ->
                cards.forEach { card ->
                    if (card is Card.AnyArithmeticCard.BundleCard) {
                        bundlingCardsList.addAll(card.cardList)
                    } else if (card is Card.AnyArithmeticCard.ArithmeticCard) {
                        bundlingCardsList.add(card)
                    }
                }
            }

            val newBundleCards =
                Card.AnyArithmeticCard.BundleCard(
                    cardList = bundlingCardsList,
                    arithmeticValue = playerBundleCard.arithmeticValue,
                    playerId = requiredActivePlayer.playerId,
                    bundleStage = bundleStage,
                )

            val newTableDeck = currentState.game.tableDeck + newBundleCards

            val newPlayerList =
                currentState.game.playerList.map { pl ->
                    var updatedPlayer = pl

                    if (pl.playerId == playerWhoHadABundle?.playerId) {
                        updatedPlayer =
                            updatedPlayer.copy(
                                bundlingCard = null,
                            )
                    }

                    if (pl.playerId == requiredActivePlayer.playerId) {
                        updatedPlayer =
                            updatedPlayer.copy(
                                bundlingCard = newBundleCards,
                                cardToBeBundled = null,
                            )
                    }

                    updatedPlayer
                }

            currentState.copy(
                game =
                    currentState.game.copy(
                        tableDeck = newTableDeck,
                        playerList = newPlayerList,
                    ),
            )
        }
    }

    fun startBundling(card: Card.AnyArithmeticCard.ArithmeticCard) {
        _state.update { currentState ->
            val activeTableCard = currentState.game.tableActiveCards.toMutableList()
            activeTableCard.add(requiredActivePlayerCard)

            val newPlayerList =
                currentState.game.playerList.map { pl ->
                    when {
                        pl.playerId == requiredActivePlayer.playerId -> {
                            pl.copy(
                                cardsHand = pl.cardsHand - requiredActivePlayerCard,
                                cardToBeBundled = card,
                                activeCard = null,
                            )
                        }

                        else -> pl
                    }
                }

            currentState.copy(
                game =
                    currentState.game.copy(
                        tableActiveCards = activeTableCard,
                        playerList = newPlayerList,
                    ),
            )
        }
    }

    fun saveState() {
        stateSavePoint = state.value.copy()
    }

    fun cancelMove() {
        stateSavePoint?.let { savedState ->
            _state.update { savedState }
        }
    }

    private fun dealInitialCard() {
        dealCards(withTableCards = true)
    }

    private fun dealCards(withTableCards: Boolean = false) {
        _state.update { currentState ->
            var newTableDeck = currentState.game.tableDeck
            val newGameDeck = currentState.game.gameDeck.toMutableList()

            val newPlayerList =
                currentState.game.playerList.map { player ->
                    val playerNewCardsHand =
                        newGameDeck.take(4).also {
                            repeat(4) {
                                newGameDeck.removeAt(0)
                            }
                        }

                    player.copy(
                        cardsHand = playerNewCardsHand,
                    )
                }

            if (withTableCards) {
                newTableDeck =
                    newGameDeck.take(4).also {
                        repeat(4) {
                            newGameDeck.removeAt(0)
                        }
                    }
            }

            currentState.copy(
                game =
                    currentState.game.copy(
                        playerList = newPlayerList,
                        gameDeck = newGameDeck,
                        tableDeck = newTableDeck,
                    ),
            )
        }
    }

    private fun addPlayer() {
        _state.update { currentState ->
            val game = currentState.game
            val player = Player()

            currentState.copy(
                game =
                    game.copy(
                        playerList = game.playerList + player,
                        playerOrderIds = game.playerOrderIds + player.playerId,
                    ),
            )
        }
    }

    private fun addPlayers(playerCount: Int = 2) {
        repeat(playerCount) {
            addPlayer()
        }
    }

    private fun selectFirstPlayer() {
        _state.update { currentState ->
            val game = currentState.game
            val firstPlayerId = game.playerOrderIds.first()

            currentState.copy(
                game =
                    game.copy(
                        playerList =
                            game.playerList.map { player ->
                                player.copy(isActive = player.playerId === firstPlayerId)
                            },
                    ),
            )
        }
    }

    fun waitToTakeArithmeticCardStage() {
        _state.update { currentState ->
            currentState.copy(
                game =
                    currentState.game.copy(
                        gameStage = Stage.TakeArithmeticCard,
                    ),
            )
        }
    }

    fun waitToTakeQueenOrKingCardStage() {
        _state.update { currentState ->
            currentState.copy(
                game =
                    currentState.game.copy(
                        gameStage = Stage.TakeQueenOrKingCard,
                    ),
            )
        }
    }

    fun waitToBundleCardStage() {
        _state.update { currentState ->
            currentState.copy(
                game =
                    currentState.game.copy(
                        gameStage = Stage.WaitToBundle,
                    ),
            )
        }
    }
}

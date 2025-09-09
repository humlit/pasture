package com.example.pastyra.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pastyra.domain.model.AnyArithmeticCard
import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.GameState
import com.example.pastyra.domain.model.JackCard
import com.example.pastyra.domain.model.KingCard
import com.example.pastyra.domain.model.Player
import com.example.pastyra.domain.model.QueenCard
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


open class GameViewModel() : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    var cardForDeleteFromDisplay by mutableStateOf<List<Card>>(emptyList())
        private set
    private val selectionMutex = Mutex()

    init {
        startGame()
    }

    private fun startGame() {
        addPlayer()
        activePlayerSelect()
        dealInitialCard()
    }

    val activePlayer: Player? get() = state.value.game.playerList.find { it.isActive }

    val activePlayerCard: Card?
        get() = requiredActivePlayer.activeCard

    val requiredActivePlayer: Player
        get() = activePlayer ?: throw AssertionError("No active player")

    val requiredActivePlayerCard: Card
        get() = requiredActivePlayer.activeCard ?: throw AssertionError("No active player card")

    fun updateActivePlayerCard(card: Card) {
        _state.update { currentState ->
            val newPlayerList = currentState.game.playerList.map { player ->
                if (player.playerId == requiredActivePlayer.playerId) {
                    player.copy(activeCard = card)
                } else {
                    player
                }
            }

            currentState.copy(
                game = currentState.game.copy(
                    playerList = newPlayerList,
                )
            )
        }
    }

    fun selectCard(card: Card) {
        viewModelScope.launch {
            _state.update { currentState ->

                val newTempPlayerBundlingCard =
                    currentState.game.temporaryPlayerBundlingCards.flatten().toMutableList()
                val newTableActiveCard = currentState.game.tableActiveCards.toMutableList()

                val newPlayerList: List<Player> = currentState.game.playerList.map { pl ->
                    if (pl.playerId == requiredActivePlayer.playerId) {
                        when {
                            currentState.game.gameStage == Stage.WaitToBundle && newTempPlayerBundlingCard.isNotEmpty() -> {
                                pl.copy(
                                    cardsHand = pl.cardsHand + newTempPlayerBundlingCard.first(),
                                    cardToTheBundle = null
                                ).also { newTempPlayerBundlingCard.removeFirstOrNull() }
                            }

                            currentState.game.gameStage == Stage.WaitToBundle && newTableActiveCard.isNotEmpty() -> {
                                pl.copy(
                                    cardsHand = pl.cardsHand + newTableActiveCard.first(),
                                    cardToTheBundle = null
                                ).also { newTableActiveCard.removeFirstOrNull() }
                            }

                            else -> pl.copy(
                                cardToTheBundle = null
                            )
                        }

                    } else {
                        pl
                    }
                }

                val combinedTableDeck =
                    currentState.game.tableDeck + newTableActiveCard + currentState.game.temporaryPlayerTakenCards + newTempPlayerBundlingCard

                updateActivePlayerCard(card)

                currentState.copy(
                    game = currentState.game.copy(
                        playerList = newPlayerList,
                        temporaryPlayerTakenCards = emptyList(),
                        temporaryPlayerBundlingCards = emptyList(),
                        tableActiveCards = emptyList(),
                        tableDeck = combinedTableDeck,
                        gameStage = Stage.DefaultStage
                    )
                )
            }
        }
    }

    fun selectTableCard(card: Card) {
        viewModelScope.launch {
            selectionMutex.withLock {
                _state.update { currentState ->
                    if (requiredActivePlayer.activeCard == null || requiredActivePlayer.activeCard is JackCard) return@update currentState

                    val newTableActiveCard = when (requiredActivePlayer.activeCard) {
                        is ArithmeticCard -> {
                            if (currentState.game.gameStage == Stage.TakeArithmeticCard) {
                                selectArithmeticTableCard(
                                    card,
                                    currentState,
                                    requiredActivePlayer.playerId
                                )
                            } else {
                                return@update currentState
                            }
                        }

                        is QueenCard, is KingCard -> {
                            if (currentState.game.gameStage == Stage.TakeQueenOrKingCard) {
                                selectQueenAndKingCard(
                                    card,
                                    currentState,
                                    requiredActivePlayer.playerId
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

                    if (newTableActiveCard.any { card -> card is AnyArithmeticCard } && activeTableCardEqualsPlayerCard(
                            requiredActivePlayer.activeCard as ArithmeticCard,
                            newTableActiveCard
                        )
                    ) {
                        newTemporaryPlayerTakenCard.addAll(newTableActiveCard)
                        newTableActiveCard.clear()
                    } else if (newTableActiveCard.any { card -> card is KingCard || card is QueenCard }) {
                        newTemporaryPlayerTakenCard.addAll(newTableActiveCard)
                        newTableActiveCard.clear()
                    }

                    currentState.copy(
                        game = currentState.game.copy(
                            tableActiveCards = newTableActiveCard,
                            tableDeck = newTableDeck,
                            temporaryPlayerTakenCards = newTemporaryPlayerTakenCard,
                        )
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

                    if (requiredActivePlayer.cardToTheBundle == null) return@update currentState

                    val newTableActiveCard = selectCardForBundle(
                        cardToBeBundled = card,
                        tableDeck = currentState.game.tableActiveCards,
                        player = requiredActivePlayer
                    )

                    val newTableDeck = tableDeck - newTableActiveCard

                    val newTempPlayerBundlingCards =
                        currentState.game.temporaryPlayerBundlingCards.toMutableList()

                    if (activeTableCardEqualsPlayerCard(
                            requiredActivePlayer.cardToTheBundle!!,
                            newTableActiveCard
                        )
                    ) {
                        newTempPlayerBundlingCards.add(newTableActiveCard.toList())
                        newTableActiveCard.clear()
                    }

                    currentState.copy(
                        game = currentState.game.copy(
                            tableActiveCards = newTableActiveCard,
                            tableDeck = newTableDeck,
                            temporaryPlayerBundlingCards = newTempPlayerBundlingCards,
                        )
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
                game = currentState.game.copy(
                    tableActiveCards = newTableActiveCards,
                    tableDeck = combinedTableDeck
                )
            )
        }
    }

    fun closeDialogBox() {
        cardForDeleteFromDisplay = emptyList()
    }

    fun putCardOnTable() {
        _state.update { currentState ->
            val updatedPlayerList = currentState.game.playerList.map { pl ->
                when {
                    pl.playerId == requiredActivePlayer.playerId -> pl.copy(
                        cardsHand = pl.cardsHand - requiredActivePlayerCard,
                        activeCard = null,
                    )

                    else -> pl
                }
            }

            val newTableActiveCard = currentState.game.tableActiveCards.toMutableList()

            if (newTableActiveCard.isNotEmpty()) {
                newTableActiveCard.clear()
            }

            val newTableDeck = currentState.game.tableDeck + requiredActivePlayerCard

            val switchedPlayerList = getSwitchedPlayerList(updatedPlayerList)

            currentState.copy(
                game = currentState.game.copy(
                    playerList = switchedPlayerList,
                    tableDeck = newTableDeck,
                    tableActiveCards = newTableActiveCard
                )
            )
        }
    }

    fun takeCard() {
        /**
         * TODO: Мне не нравится, что по окончанию взятия карты у нас делаются одинаковые
         *  преобразования стейта, но дублируются внутри каждой функции
         *  `takeArithmeticCard`, `takeQueenAndKingCard`, `takeJackCard`.
         *  Все эти преобразования должны быть тут, в `takeCard`. т.к. они идентичны.
         *  Надо подумать как это реализовать
         *  */

        when (requiredActivePlayerCard) {
            is ArithmeticCard -> {
                takeArithmeticCard()
            }

            is QueenCard, is KingCard -> {
                takeQueenAndKingCard()
            }

            is JackCard -> {
                takeJackCard()
            }
        }
    }

    private fun takeArithmeticCard() {
        _state.update { currentState ->
            val tableTemporaryCard = currentState.game.temporaryPlayerTakenCards.toMutableList()

            val newPlayerList = currentState.game.playerList.map { pl ->
                when {
                    pl.playerId == requiredActivePlayer.playerId -> pl.copy(
                        cardsTaken = pl.cardsTaken + tableTemporaryCard + requiredActivePlayerCard,
                        cardsHand = pl.cardsHand - requiredActivePlayerCard,
                        activeCard = null,
                    )

                    else -> pl
                }
            }

            tableTemporaryCard.clear()

            val switchedPlayerList = getSwitchedPlayerList(newPlayerList)

            currentState.copy(
                game = currentState.game.copy(
                    playerList = switchedPlayerList,
                    temporaryPlayerTakenCards = tableTemporaryCard,
                    gameStage = Stage.DefaultStage
                )
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
                                activeCard = null
                            )
                        }

                        else -> {
                            pl
                        }
                    }
                }

            tableTemporaryCard.clear()

            val switchedPlayerList = getSwitchedPlayerList(newPLayerList)

            currentState.copy(
                game = currentState.game.copy(
                    playerList = switchedPlayerList,
                    temporaryPlayerTakenCards = tableTemporaryCard,
                    gameStage = Stage.DefaultStage
                )
            )
        }
    }

    fun takeJackCard() {
        _state.update { currentState ->
            val mutableTableDeck = currentState.game.tableDeck.toMutableList()
            val newPlayerTakenCard = requiredActivePlayer.cardsTaken.toMutableList()

            mutableTableDeck.forEach { tableCard ->
                if (tableCard is ArithmeticCard || tableCard is JackCard) {
                    newPlayerTakenCard.add(tableCard)
                }
            }

            val newPlayerList = currentState.game.playerList.map { pl ->
                if (pl.playerId == requiredActivePlayer.playerId) {
                    pl.copy(
                        cardsTaken = newPlayerTakenCard + requiredActivePlayerCard,
                        cardsHand = requiredActivePlayer.cardsHand - requiredActivePlayerCard
                    )
                } else {
                    pl
                }
            }

            val newTableDeck = mutableTableDeck - newPlayerTakenCard

            val switchedPlayerList = getSwitchedPlayerList(newPlayerList)

            val newGame = currentState.game.copy(
                playerList = switchedPlayerList,
                tableDeck = newTableDeck,
                gameStage = Stage.DefaultStage
            )

            currentState.copy(
                game = newGame
            )
        }
    }

    fun bundleCard(playerId: Int) {
        _state.update { currentState ->

            currentState
        }
    }

    fun startBundling(card: ArithmeticCard) {
        _state.update { currentState ->
            val activeTableCard = currentState.game.tableActiveCards.toMutableList()
            activeTableCard.add(requiredActivePlayerCard)

            val newPlayerList = currentState.game.playerList.map { pl ->
                when {
                    pl.playerId == requiredActivePlayer.playerId -> {
                        pl.copy(
                            cardsHand = pl.cardsHand - requiredActivePlayerCard,
                            cardToTheBundle = card,
                            activeCard = null,
                        )
                    }

                    else -> pl
                }
            }

            currentState.copy(
                game = currentState.game.copy(
                    tableActiveCards = activeTableCard,
                    playerList = newPlayerList
                )
            )
        }
    }

    fun cancelMove() {

    }

    private fun dealInitialCard() {
        _state.update { currentState ->
            val newDeck = currentState.game.gameDeck.toMutableList()

            val newPlayer = currentState.game.playerList.map { player ->
                player.copy(cardsHand = dealCard(newDeck))
            }

            val newTable = dealCard(newDeck)

            currentState.copy(
                game = currentState.game.copy(
                    gameDeck = newDeck,
                    playerList = newPlayer,
                    tableDeck = newTable
                )
            )
        }
    }

    private fun dealCard(deck: MutableList<Card>): List<Card> {
        return deck.take(4).also {
            repeat(4) {
                deck.removeAt(0)
            }
        }
    }

    private fun addPlayer(playerCount: Int = 2) {
        val player = Player()
        val currentState = _state.value

        var currentPlayerId = player.playerId
        val newPlayerList = currentState.game.playerList.toMutableList()

        repeat(playerCount) {
            newPlayerList.add(Player(playerId = currentPlayerId))
            currentPlayerId++
        }

        val newGame = currentState.game.copy(
            playerList = newPlayerList
        )

        _state.value = currentState.copy(game = newGame)
    }

    private fun activePlayerSelect() {
        val currentState = _state.value

        val randomPlayer = currentState.game.playerList.random()

        val newPlayerList = currentState.game.playerList.map { player ->
            if (player.playerId == randomPlayer.playerId) {
                player.copy(isActive = true)
            } else {
                player.copy(isActive = false)
            }
        }

        val newGame = currentState.game.copy(
            playerList = newPlayerList
        )

        _state.value = currentState.copy(game = newGame)
    }

    fun getSwitchedPlayerList(playerList: List<Player>): List<Player> {
        return playerList.map { player ->
            player.copy(isActive = !player.isActive)
        }
    }

    fun waitToTakeArithmeticCardStage() {
        _state.update { currentState ->
            currentState.copy(
                game = currentState.game.copy(
                    gameStage = Stage.TakeArithmeticCard
                )
            )
        }
    }

    fun waitToTakeQueenOrKingCardStage() {
        _state.update { currentState ->
            currentState.copy(
                game = currentState.game.copy(
                    gameStage = Stage.TakeQueenOrKingCard
                )
            )
        }
    }

    fun waitToBundleCardStage() {
        _state.update { currentState ->
            currentState.copy(
                game = currentState.game.copy(
                    gameStage = Stage.WaitToBundle
                )
            )
        }
    }
}
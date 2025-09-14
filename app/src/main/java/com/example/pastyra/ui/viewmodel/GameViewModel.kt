package com.example.pastyra.ui.viewmodel

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


open class GameViewModel() : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    @get:VisibleForTesting
    internal val mutableState: MutableStateFlow<GameState> get() = _state

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

    val requireActivePlayerBundleCard: Card.AnyArithmeticCard.ArithmeticCard
        get() = requiredActivePlayer.cardToBeBundled
            ?: throw AssertionError("No active player bundle card")

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
                                    cardToBeBundled = null
                                ).also { newTempPlayerBundlingCard.removeFirstOrNull() }
                            }

                            currentState.game.gameStage == Stage.WaitToBundle && newTableActiveCard.isNotEmpty() -> {
                                pl.copy(
                                    cardsHand = pl.cardsHand + newTableActiveCard.first(),
                                    cardToBeBundled = null
                                ).also { newTableActiveCard.removeFirstOrNull() }
                            }

                            else -> pl.copy(
                                cardToBeBundled = null
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
                    val activePlayer = requiredActivePlayer

                    if (activePlayer.activeCard == null || activePlayer.activeCard is Card.JackCard) return@update currentState

                    val newTableActiveCard = when (activePlayer.activeCard) {
                        is Card.AnyArithmeticCard.ArithmeticCard -> {
                            if (currentState.game.gameStage == Stage.TakeArithmeticCard) {
                                selectArithmeticTableCard(
                                    card, currentState, activePlayer.playerId
                                )
                            } else {
                                return@update currentState
                            }
                        }

                        is Card.QueenCard, is Card.KingCard -> {
                            if (currentState.game.gameStage == Stage.TakeQueenOrKingCard) {
                                selectQueenAndKingCard(
                                    card, currentState, activePlayer.playerId
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

                    if (newTableActiveCard.any { card -> card is Card.AnyArithmeticCard } && activeTableCardEqualsPlayerCard(
                            activePlayer.activeCard as Card.AnyArithmeticCard.ArithmeticCard,
                            newTableActiveCard
                        )) {
                        newTemporaryPlayerTakenCard.addAll(newTableActiveCard)
                        newTableActiveCard.clear()
                    } else if (newTableActiveCard.any { card -> card is Card.KingCard || card is Card.QueenCard }) {
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

                    val activePlayer = requiredActivePlayer

                    if (activePlayer.cardToBeBundled == null) return@update currentState

                    val newTableActiveCard = selectCardForBundle(
                        cardToBeBundled = card,
                        tableDeck = currentState.game.tableActiveCards,
                        player = activePlayer
                    )

                    val newTableDeck = tableDeck - newTableActiveCard

                    val newTempPlayerBundlingCards =
                        currentState.game.temporaryPlayerBundlingCards.toMutableList()

                    if (activeTableCardEqualsPlayerCard(
                            activePlayer.cardToBeBundled, newTableActiveCard
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
                    tableActiveCards = newTableActiveCards, tableDeck = combinedTableDeck
                )
            )
        }
    }

    fun closeDialogBox() {
        cardForDeleteFromDisplay = emptyList()
    }

    fun finishMove() {
//        NOTE: Если решишься взяться за это, будь готов к большому рефакторингу и багфиксам ;)
//        if (действие == завязка) {
//            finishBundle() -> меняет карты стола и активного игрока, а также temporary карты
//        }
//
//        if (действие == взятка) {
//            finishTake() (ну или takeCard(), но оттуда надо будет убрать лишнюю логику)
//            -> меняет карты стола и активного игрока, а также temporary карты
//        }
//
//        далее общие действия завершения хода:
//        _state.update { currentState ->
//            currentState.copy(
//                game = currentState.game.copy(
//                    gameStage = Stage.DefaultStage,
//                    playerList = нужно сменить статус активности игрока, тут может быть подъебка,
//                                 если выше playerList ты обновляешь, нужно будет проверить
//                )
//            )
//        }

        switchActivePlayer()
        tryDealNewCards()
        removeState()

    }

    fun removeState() {
        _state.update { currentState ->

            currentState.copy(
                game = currentState.game.copy(
                    temporaryPlayerTakenCards = emptyList(),
                    temporaryPlayerBundlingCards = emptyList(),
                    tableActiveCards = emptyList(),
                    gameStage = Stage.DefaultStage
                )
            )
        }
    }

    fun tryDealNewCards() {
        if (state.value.game.playerList.all { it.cardsHand.isEmpty() }) {
            dealCards()
        }
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

            val newTableDeck = currentState.game.tableDeck + requiredActivePlayerCard

            currentState.copy(
                game = currentState.game.copy(
                    playerList = updatedPlayerList,
                    tableDeck = newTableDeck,
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
                game = currentState.game.copy(
                    lastTakenCardPlayer = requiredActivePlayer
                )
            )
        }
    }

    fun switchActivePlayer() {
        _state.update { currentState ->

            val playerList = currentState.game.playerList
            val currentId = requiredActivePlayer.playerId

            val nextId = if (currentId >= playerList.size) 1 else currentId + 1

            val newPlayerList = playerList.map { player ->
                player.copy(isActive = player.playerId == nextId)
            }

            currentState.copy(
                game = currentState.game.copy(
                    playerList = newPlayerList,
                )
            )
        }
    }

    private fun takeAnyArithmeticCard() {
        _state.update { currentState ->
            val newTemporaryPlayerTakenCards =
                currentState.game.temporaryPlayerTakenCards.toMutableList()

            val newPlayerList = currentState.game.playerList.map { pl ->
                var updatedPlayer = pl

                if (newTemporaryPlayerTakenCards.any { it == pl.bundlingCard }) {
                    val currentPlayerBundling =
                        currentState.game.playerList.find { it.bundlingCard == pl.bundlingCard }
                    when {
                        pl.playerId == currentPlayerBundling?.playerId -> {
                            updatedPlayer = updatedPlayer.copy(
                                bundlingCard = null,
                            )
                        }
                    }
                }

                when {
                    pl.playerId == requiredActivePlayer.playerId -> {
                        updatedPlayer = updatedPlayer.copy(
                            cardsTaken = pl.cardsTaken + newTemporaryPlayerTakenCards + requiredActivePlayerCard,
                            cardsHand = pl.cardsHand - requiredActivePlayerCard,
                            activeCard = null,
                        )
                    }
                }

                updatedPlayer
            }

            currentState.copy(
                game = currentState.game.copy(
                    playerList = newPlayerList,
                )
            )
        }
    }

    fun takeQueenAndKingCard() {
        _state.update { currentState ->
            val tableTemporaryCard = currentState.game.temporaryPlayerTakenCards.toMutableList()
            val cardToPlay = requiredActivePlayerCard

            val newPLayerList = currentState.game.playerList.map { pl ->
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

            currentState.copy(
                game = currentState.game.copy(
                    playerList = newPLayerList,
                )
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

            val newTableDeck = tableDeck - newPlayerTakenCard

            currentState.copy(
                game = currentState.game.copy(
                    playerList = newPlayerList,
                    tableDeck = newTableDeck,
                )
            )
        }
    }

    fun bundleCard() {
        _state.update { currentState ->
            val tempCards = currentState.game.temporaryPlayerBundlingCards.flatten()
            val temporaryBundleCards = currentState.game.temporaryPlayerBundlingCards
            val playerBundleCard = requireActivePlayerBundleCard

            if (tempCards.isEmpty()) return@update currentState

            val bundleStage = tempCards.maxOf {
                if (it is Card.AnyArithmeticCard.BundleCard) (temporaryBundleCards.size - 1) + it.bundleStage
                else temporaryBundleCards.size
            }

            val newBundleCards = Card.AnyArithmeticCard.BundleCard(
                cardList = tempCards,
                point = tempCards.sumOf { card ->
                    card.point
                },
                arithmeticValue = playerBundleCard.arithmeticValue,
                playerId = requiredActivePlayer.playerId,
                bundleStage = bundleStage
            )

            val newTableDeck = currentState.game.tableDeck + newBundleCards

            val newPlayerList = currentState.game.playerList.map { pl ->
                when {
                    pl.playerId == requiredActivePlayer.playerId -> pl.copy(
                        bundlingCard = newBundleCards,
                        cardToBeBundled = null,
                    )

                    else -> pl
                }
            }

            currentState.copy(
                game = currentState.game.copy(
                    tableDeck = newTableDeck,
                    playerList = newPlayerList,
                )
            )
        }
    }

    fun startBundling(card: Card.AnyArithmeticCard.ArithmeticCard) {
        _state.update { currentState ->
            val activeTableCard = currentState.game.tableActiveCards.toMutableList()
            activeTableCard.add(requiredActivePlayerCard)

            val newPlayerList = currentState.game.playerList.map { pl ->
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
                game = currentState.game.copy(
                    tableActiveCards = activeTableCard, playerList = newPlayerList
                )
            )
        }
    }

    private fun dealInitialCard() {
        dealCards(withTableCards = true)
    }

    private fun dealCards(withTableCards: Boolean = false) {
        _state.update { currentState ->
            var newTableDeck = currentState.game.tableDeck
            val newGameDeck = currentState.game.gameDeck.toMutableList()

            val newPlayerList = currentState.game.playerList.map { player ->
                val playerNewCardsHand = newGameDeck.take(4).also {
                    repeat(4) {
                        newGameDeck.removeAt(0)
                    }
                }

                player.copy(
                    cardsHand = playerNewCardsHand
                )
            }

            if (withTableCards) {
                newTableDeck = newGameDeck.take(4).also {
                    repeat(4) {
                        newGameDeck.removeAt(0)
                    }
                }
            }

            currentState.copy(
                game = currentState.game.copy(
                    playerList = newPlayerList, gameDeck = newGameDeck, tableDeck = newTableDeck
                )
            )
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
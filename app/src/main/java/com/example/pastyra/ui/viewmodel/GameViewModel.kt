package com.example.pastyra.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    val requiredActivePlayer: Player
        get() = activePlayer ?: throw AssertionError("No active player")

    val requiredActivePlayerCard: Card
        get() = requiredActivePlayer.activeCard ?: throw AssertionError("No active player card")


    fun updateActivePlayerCard(card: Card) {
        _state.update { currentState ->
            val newPlayerList = currentState.game.playerList.map { player ->
                if (player.playerId == requiredActivePlayer.playerId) player.copy(activeCard = card)
                else player
            }

            currentState.copy(
                game = currentState.game.copy(
                    playerList = newPlayerList,
                )
            )
        }
    }

    fun selectCard(card: Card) {
        _state.update { currentState ->

            val combinedTableDeck = currentState.game.tableDeck +
                    currentState.game.tableActiveCard +
                    currentState.game.temporaryPlayerTakenCard

            updateActivePlayerCard(card)
            currentState.copy(
                game = currentState.game.copy(
                    temporaryPlayerTakenCard = emptyList(),
                    tableActiveCard = emptyList(),
                    tableDeck = combinedTableDeck,
                    gameStage = Stage.DefaultStage
                )
            )
        }
    }

    fun selectTableCard(card: Card) {
        viewModelScope.launch {
            selectionMutex.withLock {
                _state.update { currentState ->

                    if (requiredActivePlayer.activeCard == null || requiredActivePlayer.activeCard is JackCard) return@update currentState

                    val newActiveCards = when (requiredActivePlayer.activeCard) {
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

                    val newTableDeck = currentState.game.tableDeck.toMutableList() - newActiveCards

                    val newTemporaryPlayerTakenCard =
                        currentState.game.temporaryPlayerTakenCard.toMutableList()

                    if (newActiveCards.any { it !is ArithmeticCard }) {
                        newTemporaryPlayerTakenCard.addAll(newActiveCards)
                        newActiveCards.clear()
                    } else if (newActiveCards.any { it is ArithmeticCard } && activeTableCardEqualsPlayerCard(
                            requiredActivePlayer.activeCard as ArithmeticCard,
                            newActiveCards
                        )
                    ) {
                        newTemporaryPlayerTakenCard.addAll(newActiveCards)
                        newActiveCards.clear()
                    }

                    currentState.copy(
                        game = currentState.game.copy(
                            tableActiveCard = newActiveCards,
                            tableDeck = newTableDeck,
                            temporaryPlayerTakenCard = newTemporaryPlayerTakenCard
                        )
                    )
                }
            }
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

            val newTableActiveCard = currentState.game.tableActiveCard.toMutableList()

            if (newTableActiveCard.isNotEmpty()) {
                newTableActiveCard.clear()
            }

            val newTableDeck = currentState.game.tableDeck + requiredActivePlayerCard

            val switchedPlayerList = getSwitchedPlayerList(updatedPlayerList)

            currentState.copy(
                game = currentState.game.copy(
                    playerList = switchedPlayerList,
                    tableDeck = newTableDeck,
                    tableActiveCard = newTableActiveCard
                )
            )
        }
    }

    fun takeCard() {
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
            val tableTemporaryCard = currentState.game.temporaryPlayerTakenCard.toMutableList()

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
                    temporaryPlayerTakenCard = tableTemporaryCard
                )
            )
        }
    }

    fun takeQueenAndKingCard() {
        _state.update { currentState ->
            val tableTemporaryCard = currentState.game.temporaryPlayerTakenCard.toMutableList()
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
                    temporaryPlayerTakenCard = tableTemporaryCard
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
            )

            currentState.copy(
                game = newGame
            )
        }
    }

    fun bundleCard(playerId: Int) {

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

    fun resetStage() {
        _state.update { currentState ->
            currentState.copy(
                game = currentState.game.copy(
                    gameStage = Stage.DefaultStage
                )
            )
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
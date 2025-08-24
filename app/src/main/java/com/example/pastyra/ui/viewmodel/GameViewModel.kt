package com.example.pastyra.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pastyra.domain.usercase.selectArithmeticTableCard
import com.example.pastyra.domain.usercase.selectQueenAndKingCard
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.GameState
import com.example.pastyra.domain.model.Player
import com.example.pastyra.domain.model.Stage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


open class GameViewModel() : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    init {
        startGame()
    }

    private fun startGame() {
        addPlayer()
        activePlayerSelect()
        dealInitialCard()
    }

    fun selectCard(playerId: Int, card: Card) {
        _state.update { currentState ->
            val newTableActiveCard = currentState.game.tableActiveCard.toMutableList()

            if (newTableActiveCard.isNotEmpty()) {
                newTableActiveCard.clear()
            }

            val newPlayerList = currentState.game.playerList.map { player ->
                if (player.playerId == playerId) {
                    val newActiveCard = card
                    player.copy(activeCard = newActiveCard)
                } else {
                    player
                }
            }

            currentState.copy(
                game = currentState.game.copy(
                    playerList = newPlayerList,
                    tableActiveCard = newTableActiveCard
                )
            )
        }
    }

    fun selectTableCard(card: Card, playerId: Int) {
        _state.update { currentState ->
            val currentPlayer = currentState.game.playerList.find { it.playerId == playerId }
            if (currentPlayer == null || currentPlayer.activeCard == null || currentPlayer.activeCard.rank.toString() == "JACK") return@update currentState

            val newActiveCards = when {
                currentPlayer.activeCard.arithmeticValue != null -> {
                    if (currentState.game.gameStage == Stage.WaitToTakeArithmeticCard) {
                        selectArithmeticTableCard(card, currentState, playerId)
                    } else {
                        return@update currentState
                    }
                }

                currentPlayer.activeCard.rank.toString() == "QUEEN" || currentPlayer.activeCard.rank.toString() == "KING" -> {
                    selectQueenAndKingCard(card, currentState, playerId)
                }

                else -> throw IllegalArgumentException("")
            }

            currentState.copy(
                game = currentState.game.copy(tableActiveCard = newActiveCards)
            )
        }
    }

    fun putCardOnTable(playerId: Int) {
        _state.update { currentState ->
            val currentPlayer = currentState.game.playerList.find { it.playerId == playerId }
            if (currentPlayer == null || currentPlayer.activeCard == null) return@update currentState

            val cardToPlay = currentPlayer.cardsHand.find { it.id == currentPlayer.activeCard.id }
                ?: return@update currentState

            val updatePlayerList = currentState.game.playerList.map { pl ->
                when {
                    pl.playerId == playerId -> pl.copy(
                        cardsHand = pl.cardsHand - cardToPlay,
                        activeCard = null
                    )

                    else -> pl
                }
            }

            val newTableActiveCard = currentState.game.tableActiveCard.toMutableList()

            if (newTableActiveCard.isNotEmpty()) {
                newTableActiveCard.clear()
            }

            val newTableDeck = currentState.game.tableDeck + cardToPlay

            currentState.copy(
                game = currentState.game.copy(
                    playerList = updatePlayerList,
                    tableDeck = newTableDeck,
                    tableActiveCard = newTableActiveCard
                )
            )
        }
    }

    fun takeCard(playerId: Int) {
        val currentPlayer = _state.value.game.playerList.find { it.playerId == playerId }

        when {
            currentPlayer?.activeCard?.arithmeticValue != null -> takeArithmeticCard(playerId)

            (currentPlayer?.activeCard?.rank.toString() == "QUEEN" || currentPlayer?.activeCard?.rank.toString() == "KING") -> takeQueenAndKingCard(
                playerId
            )

            currentPlayer?.activeCard?.rank.toString() == "JACK" -> takeJackCard(playerId)
        }
    }

    private fun takeArithmeticCard(playerId: Int) {
        _state.update { currentState ->

            val currentPlayer = currentState.game.playerList.find { it.playerId == playerId }
            if (currentPlayer == null || currentPlayer.activeCard == null) return@update currentState

            currentState
        }
    }

    fun takeQueenAndKingCard(playerId: Int) {
        _state.update { currentState ->

            val currentPlayer = currentState.game.playerList.find { it.playerId == playerId }
            if (currentPlayer == null || currentPlayer.activeCard == null) return@update currentState

            val cardToPlay = currentPlayer.activeCard

            val newPLayerList =
                if (currentState.game.tableActiveCard.last().rank == currentPlayer.activeCard.rank) {
                    currentState.game.playerList.map { pl ->
                        when {
                            pl.playerId == currentPlayer.playerId -> {
                                pl.copy(
                                    cardsHand = pl.cardsHand - cardToPlay,
                                    activeCard = null
                                )
                            }

                            else -> {
                                pl
                            }
                        }
                    }
                } else {
                    currentState.game.playerList.map { pl ->
                        pl
                    }
                }

            val newTableDeck = currentState.game.tableDeck - currentState.game.tableActiveCard

            val newGame = currentState.game.copy(
                playerList = newPLayerList,
                tableDeck = newTableDeck
            )

            currentState.copy(
                game = newGame
            )
        }
    }

    fun takeJackCard(playerId: Int) {
        _state.update { currentState ->

            val currentPlayer = currentState.game.playerList.find { it.playerId == playerId }
            if (currentPlayer == null || currentPlayer.activeCard == null) return@update currentState

            val mutableTableDeck = currentState.game.tableDeck.toMutableList()
            val newPlayerTakenCard = currentPlayer.cardsTaken.toMutableList()

            mutableTableDeck.forEach { tableCard ->
                if (tableCard.canJackPickUp) {
                    newPlayerTakenCard.add(tableCard)
                }
            }

            val newPlayerList = currentState.game.playerList.map { pl ->
                if (pl.playerId == playerId) {
                    pl.copy(
                        cardsTaken = newPlayerTakenCard + currentPlayer.activeCard,
                        cardsHand = currentPlayer.cardsHand - currentPlayer.activeCard
                    )
                } else {
                    pl
                }
            }

            val newTableDeck = mutableTableDeck - newPlayerTakenCard

            val updatedActivePlayer = newPlayerList.find { it.playerId == playerId }

            val newGame = currentState.game.copy(
                playerList = newPlayerList,
                tableDeck = newTableDeck,
                activePlayer = updatedActivePlayer
            )

            currentState.copy(
                game = newGame
            )
        }
    }

    fun bundleCard(playerId: Int) {

    }

    private fun dealInitialCard() {
        val currentState = _state.value

        val newDeck = currentState.game.gameDeck.toMutableList()
        val newPlayer = currentState.game.playerList.map { player ->
            player.copy(cardsHand = dealCard(newDeck))
        }

        val newTable = dealCard(newDeck)

        val newGame = currentState.game.copy(
            playerList = newPlayer,
            gameDeck = newDeck,
            tableDeck = newTable,
        )

        _state.value = currentState.copy(game = newGame)
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

        val updatedActivePlayer = newPlayerList.find { it.playerId == randomPlayer.playerId }

        val newGame = currentState.game.copy(
            activePlayer = updatedActivePlayer,
            playerList = newPlayerList
        )

        _state.value = currentState.copy(game = newGame)
    }

    fun switchActivePlayer() {
        _state.update { currentState ->

            val playerList = currentState.game.playerList
            val currentId = currentState.game.activePlayer?.playerId ?: 1

            val nextId = if (currentId >= playerList.size) 1 else currentId + 1

            val newPlayerList = playerList.map { player ->
                player.copy(isActive = player.playerId == nextId)
            }

            currentState.copy(
                game = currentState.game.copy(
                    playerList = newPlayerList,
                    activePlayer = newPlayerList.first { it.playerId == nextId }
                )
            )
        }
    }

    fun waitingStage() {
        _state.update { currentState ->
            currentState.copy(
                game = currentState.game.copy(
                    gameStage = Stage.Waiting
                )
            )
        }
    }

    fun waitToTakeArithmeticCardStage() {
        _state.update { currentState ->
            currentState.copy(
                game = currentState.game.copy(
                    gameStage = Stage.WaitToTakeArithmeticCard
                )
            )
        }
    }

    fun waitToBundleStage() {
        _state.update { currentState ->
            currentState.copy(
                game = currentState.game.copy(
                    gameStage = Stage.WaitToBundle
                )
            )
        }
    }

    fun readyToFinishStage() {
        _state.update { currentState ->
            currentState.copy(
                game = currentState.game.copy(
                    gameStage = Stage.ReadyToFinish
                )
            )
        }
    }
}
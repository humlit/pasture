package com.example.pastyra.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.GameState
import com.example.pastyra.domain.model.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


open class GameViewModel() : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state.asStateFlow()

    val temporaryPlayerTakenCardList = mutableStateListOf<Card>()

    init {
        startGame()
        Log.d("PLAYER_CHECK", _state.value.game.playerList.toString())
    }

    private fun startGame() {
        addPlayer()
        activePlayerSelect()
        dealInitialCard()
    }

    fun selectCard(playerId: Int, card: Card) {
        _state.update { currentState ->
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
                    playerList = newPlayerList
                )
            )
        }
    }

    fun selectTableCard(card: Card, playerId: Int) {
        _state.update { currentState ->
            val currentPlayer = currentState.game.playerList.find { it.playerId == playerId }
            if (currentPlayer == null || currentPlayer.activeCard == null) return@update currentState

            val newActiveCards = currentState.game.tableActiveCard.toMutableList()
            if (newActiveCards.any { it.id == card.id }) {
                newActiveCards.removeAll { it.id == card.id }
            } else {
                newActiveCards.add(card)
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

            val newTableDeck = currentState.game.tableDeck + cardToPlay

            currentState.copy(
                game = currentState.game.copy(
                    playerList = updatePlayerList,
                    tableDeck = newTableDeck
                )
            )
        }
    }

    fun takeCard(playerId: Int, card: Card) {
        when {
            card.arithmeticValue != null -> takeArithmeticCard(playerId)
            else -> throw IllegalArgumentException("Try again!")
        }
    }

    private fun takeArithmeticCard(playerId: Int) {
        _state.update { currentState ->

            val currentPlayer = currentState.game.playerList.find { it.playerId == playerId }
            if (currentPlayer == null || currentPlayer.activeCard == null) return@update currentState

            val defaultPlayerTakenCard = currentPlayer.cardsTaken
            val newPlayerTakenCard = currentPlayer.cardsTaken.toMutableList()
            val currentTableDeck = currentState.game.tableDeck.toMutableList()
            val currentTableActiveCard = currentState.game.tableActiveCard.toMutableList()

            if (currentTableActiveCard.last().arithmeticValue == currentPlayer.activeCard.arithmeticValue) {
                temporaryPlayerTakenCardList.add(currentTableActiveCard.last())
            }

            if (currentTableActiveCard.sumBy {
                    it.arithmeticValue ?: 0
                } == currentPlayer.activeCard.arithmeticValue) {
                newPlayerTakenCard.addAll(currentTableActiveCard)
            }

            val newPlayerList = currentState.game.playerList.map { player ->
                when {
                    player.playerId == playerId -> player.copy(
                        cardsTaken = newPlayerTakenCard,
                        cardsHand = player.cardsHand - player.activeCard!!
                    )

                    else -> player
                }
            }

            currentState.copy(
                game = currentState.game.copy(
                    playerList = newPlayerList
                )
            )
        }
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

        val activePlayer = currentState.game.playerList.random()

        val newPlayerList = currentState.game.playerList.map { player ->
            if (player.playerId == activePlayer.playerId) {
                activePlayer.copy(isActive = true)
            } else player
        }

        val newGame =
            currentState.game.copy(activePlayer = activePlayer, playerList = newPlayerList)

        _state.value = currentState.copy(game = newGame)
    }

    fun switchActivePlayer() {

    }
}
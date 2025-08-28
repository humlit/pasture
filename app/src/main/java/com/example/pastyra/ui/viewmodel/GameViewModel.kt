package com.example.pastyra.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.CardWithRank
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
            val tableCard = currentState.game.tableDeck.toMutableList()
            val temporaryTableCard = currentState.game.temporaryPlayerTakenCard.toMutableList()

            /*
            ОН ВОЗВРАЩАЕТ КАРТЫ НА СТОЛ ИЗ ЗА ЭТОЙ ФУНКЦИИ 100% -> ИСПРАВИТЬ НАХУЙ -> ВЫЯВЛЕНО -> ВОЗВРАЩАЕТ НА СТОЛ ИЗ ЗА ТОГО, ЧТО КАРТЫ ИЗ ACTIVETABLECARD
            НЕ УСПЕВАЮТ ОТПРАВИТЬСЯ В temporaryPlayerTakenCard И ОНИ ОСТАЮТСЯ ТАМ И ПРИ СМЕНЕ АКТИВНОЙ КАРТЫ УЖЕ У ДРУГОГО ИГРОКА, КАРТЫ ВОЗВРАЩАЮТСЯ
            НА СТОЛ
             */

            if (newTableActiveCard.isNotEmpty()) {
                newTableActiveCard.forEach {
                    tableCard.add(it)
                }

                newTableActiveCard.clear()
            }

            when {
                newTableActiveCard.isNotEmpty() && temporaryTableCard.isNotEmpty() -> {
                    tableCard.addAll(newTableActiveCard)
                    tableCard.addAll(temporaryTableCard)
                }

                temporaryTableCard.isNotEmpty() -> {
                    tableCard.addAll(temporaryTableCard)
                }

                newTableActiveCard.isNotEmpty() -> {
                    tableCard.addAll(newTableActiveCard)
                }
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
                    temporaryPlayerTakenCard = emptyList(),
                    tableActiveCard = emptyList(),
                    tableDeck = tableCard,
                    gameStage = Stage.DefaultStage
                )
            )
        }
    }

    fun selectTableCard(card: Card, playerId: Int) {
        _state.update { currentState ->
            val currentPlayer = currentState.game.playerList.find { it.playerId == playerId }
            if (currentPlayer == null || currentPlayer.activeCard == null || currentPlayer.activeCard is JackCard) return@update currentState

            val newActiveCards = when (currentPlayer.activeCard) {
                is ArithmeticCard -> {
                    if (currentState.game.gameStage == Stage.TakeArithmeticCard) {
                        selectArithmeticTableCard(card, currentState, playerId)
                    } else {
                        return@update currentState
                    }
                }

                is QueenCard, is KingCard -> {
                    if (currentState.game.gameStage == Stage.TakeQueenOrKingCard) {
                        selectQueenAndKingCard(card, currentState, playerId)
                    } else {
                        return@update currentState
                    }
                }

                else -> throw IllegalArgumentException("")
            }

            val newTableDeck = currentState.game.tableDeck.toMutableList() - newActiveCards

            val newTemporaryPlayerTakenCard =
                currentState.game.temporaryPlayerTakenCard.toMutableList()

            val tableCardChecker = activeTableCardEqualsPlayerCard(
                currentPlayer.activeCard as ArithmeticCard,
                currentState.game.tableActiveCard
            )

            if (tableCardChecker) {
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

        when (currentPlayer?.activeCard) {
            is ArithmeticCard -> {
                takeArithmeticCard(playerId)
            }

            is QueenCard, is KingCard -> {
                takeQueenAndKingCard(
                    playerId
                )
            }

            is JackCard -> {
                takeJackCard(playerId)
            }
        }
    }

    private fun takeArithmeticCard(playerId: Int) {
        _state.update { currentState ->

            val currentPlayer = currentState.game.playerList.find { it.playerId == playerId }
            if (currentPlayer == null || currentPlayer.activeCard == null) return@update currentState

            val cardToPlay = currentPlayer.activeCard
            val tableTemporaryCard = currentState.game.temporaryPlayerTakenCard.toMutableList()

            val newPlayerList = currentState.game.playerList.map { pl ->
                when {
                    pl.playerId == currentPlayer.playerId -> pl.copy(
                        cardsTaken = pl.cardsTaken + tableTemporaryCard,
                        cardsHand = pl.cardsHand - cardToPlay,
                        activeCard = null,
                    )

                    else -> pl
                }
            }

            tableTemporaryCard.clear()

            currentState.copy(
                game = currentState.game.copy(
                    playerList = newPlayerList,
                    temporaryPlayerTakenCard = tableTemporaryCard
                )
            )
        }
    }

    fun takeQueenAndKingCard(playerId: Int) {
        _state.update { currentState ->

            val currentPlayer = currentState.game.playerList.find { it.playerId == playerId }
            if (currentPlayer == null || currentPlayer.activeCard == null) return@update currentState

            val cardToPlay = currentPlayer.activeCard
            val lastTableCard = currentState.game.tableActiveCard.lastOrNull()

            val newPLayerList =
                if (lastTableCard is CardWithRank && currentPlayer.activeCard is CardWithRank) {
                    if (lastTableCard.rank == currentPlayer.activeCard.rank) {
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
                        return@update currentState
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
                if (tableCard is ArithmeticCard || tableCard is JackCard) {
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

    fun cancelMove() {

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
                    activePlayer = newPlayerList.first { it.playerId == nextId },
                    gameStage = Stage.DefaultStage
                )
            )
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
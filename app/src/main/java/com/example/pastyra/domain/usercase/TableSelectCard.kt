package com.example.pastyra.domain.usercase

import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.CardWithRank
import com.example.pastyra.domain.model.GameState
import com.example.pastyra.domain.model.KingCard
import com.example.pastyra.domain.model.QueenCard
import com.example.pastyra.domain.model.safeRank

fun selectArithmeticTableCard(card: Card, state: GameState, playerId: Int): MutableList<Card> {
    val newTableActiveCard = state.game.tableActiveCard.toMutableList()
    val currentPlayer = state.game.playerList.find { it.playerId == playerId }

    if (newTableActiveCard.any { it.id == card.id }) {
        newTableActiveCard.removeAll { it.id == card.id }
    } else if (card is ArithmeticCard) {
        newTableActiveCard.add(card)
    }

    val cardVerificationToTake = isSelectable(currentPlayer?.activeCard, newTableActiveCard)

    if (!cardVerificationToTake && newTableActiveCard.isNotEmpty()) {
        newTableActiveCard.removeLastOrNull()
    }

    return newTableActiveCard
}

fun selectQueenAndKingCard(card: Card, state: GameState, playerId: Int): MutableList<Card> {
    val newTableActiveCard = state.game.tableActiveCard.toMutableList()

    if (newTableActiveCard.isNotEmpty() && card is CardWithRank) {
        if(card.rank == state.game.activePlayer?.activeCard?.safeRank){
            newTableActiveCard.clear()
        }
    }

    val currentPlayer = state.game.playerList.find { it.playerId == playerId }

    if (card is CardWithRank && currentPlayer?.activeCard is CardWithRank) {
        if (card.rank == currentPlayer.activeCard.rank) {
            newTableActiveCard.add(card)
        }
    }

    return newTableActiveCard
}
package com.example.pastyra.domain.usercase

import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.CardWithRank
import com.example.pastyra.domain.model.GameState

fun selectArithmeticTableCard(card: Card, state: GameState, playerId: Int): MutableList<Card> {
    val newTableActiveCard = state.game.tableActiveCard.toMutableList()
    val currentPlayer = state.game.playerList.find { it.playerId == playerId }

    if (card is ArithmeticCard) {
        newTableActiveCard.add(card)
    }

    val cardVerificationToTake =
        isPlayerCanTakeAnyoneCard(currentPlayer?.activeCard as ArithmeticCard, newTableActiveCard)

    if (!cardVerificationToTake && newTableActiveCard.isNotEmpty()) {
        newTableActiveCard.removeLastOrNull()
    }

    return newTableActiveCard
}

fun selectQueenAndKingCard(card: Card, state: GameState, playerId: Int): MutableList<Card> {
    val newTableActiveCard = state.game.tableActiveCard.toMutableList()
    val currentPlayer = state.game.playerList.find { it.playerId == playerId }

    if (card is CardWithRank && currentPlayer?.activeCard is CardWithRank && card.rank == currentPlayer.activeCard.rank) {
        newTableActiveCard.add(card)
    }

    return newTableActiveCard
}

fun selectCardForBundle(card: Card, state: GameState): MutableList<Card> {
    val newTableActiveCard = state.game.tableActiveCard.toMutableList()

    if (card !is CardWithRank) {
        newTableActiveCard.add(card)
    }

    return newTableActiveCard
}

fun selectCardForBundle() {

}
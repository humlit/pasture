package com.example.pastyra.domain.usercase

import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.GameState
import com.example.pastyra.domain.model.Player
import com.example.pastyra.domain.model.SimpleCard

fun selectArithmeticTableCard(
    card: Card, state: GameState, playerId: Int
): MutableList<Card.AnyArithmeticCard> {
    val newTableActiveCards =
        state.game.tableActiveCards.filterIsInstance<Card.AnyArithmeticCard>().toMutableList()
    val currentPlayer = state.game.playerList.find { it.playerId == playerId }

    if (card is Card.AnyArithmeticCard) {
        newTableActiveCards.add(card)
    }

    val cardVerificationToTake = isPlayerCanTakeAnyoneCard(
        currentPlayer?.activeCard as Card.AnyArithmeticCard.ArithmeticCard, newTableActiveCards
    )

    if (!cardVerificationToTake && newTableActiveCards.isNotEmpty()) {
        newTableActiveCards.removeLastOrNull()
    }

    return newTableActiveCards
}

fun selectQueenAndKingCard(card: Card, state: GameState, playerId: Int): MutableList<Card> {
    val newTableActiveCards = state.game.tableActiveCards.toMutableList()
    val currentPlayer = state.game.playerList.find { it.playerId == playerId }

    if (card is SimpleCard && currentPlayer?.activeCard is SimpleCard && card.rank == currentPlayer.activeCard.rank) {
        newTableActiveCards.add(card)
    }

    return newTableActiveCards
}

fun selectCardForBundle(
    cardToBeBundled: Card, tableDeck: List<Card>, player: Player
): MutableList<Card.AnyArithmeticCard> {
    val newTableActiveCard = tableDeck.filterIsInstance<Card.AnyArithmeticCard>().toMutableList()

    if (cardToBeBundled is Card.AnyArithmeticCard) {
        newTableActiveCard.add(cardToBeBundled)
    }

    val cardVerificationToTake = isPlayerCanTakeAnyoneCard(
        player.cardToBeBundled as Card.AnyArithmeticCard.ArithmeticCard, newTableActiveCard
    )

    if (!cardVerificationToTake && newTableActiveCard.isNotEmpty()) {
        newTableActiveCard.removeLastOrNull()
    }

    return newTableActiveCard
}

fun removeTableCards(card: Card, chosenTableCards: List<Card>): List<Card> {
    val newChosenTableCards = chosenTableCards.toMutableList()


    return newChosenTableCards
}
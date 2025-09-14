package com.example.pastyra.domain.usercase

import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.GameState
import com.example.pastyra.domain.model.Player
import com.example.pastyra.domain.model.SimpleCard

fun selectArithmeticTableCard(
    card: Card, state: GameState, player: Player
): MutableList<Card.AnyArithmeticCard> {
    val newTableActiveCards =
        state.game.tableActiveCards.filterIsInstance<Card.AnyArithmeticCard>().toMutableList()

    if (card is Card.AnyArithmeticCard) {
        newTableActiveCards.add(card)
    }

    val cardVerificationToTake = isPlayerCanTakeAnyoneCard(
        player.activeCard as Card.AnyArithmeticCard.ArithmeticCard, newTableActiveCards
    )

    if (!cardVerificationToTake && newTableActiveCards.isNotEmpty()) {
        newTableActiveCards.removeLastOrNull()
    }

    return newTableActiveCards
}

fun selectQueenAndKingCard(card: Card, state: GameState, player: Player): MutableList<Card> {
    val newTableActiveCards = state.game.tableActiveCards.toMutableList()

    if (card is SimpleCard && player.activeCard is SimpleCard && card.rank == player.activeCard.rank) {
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
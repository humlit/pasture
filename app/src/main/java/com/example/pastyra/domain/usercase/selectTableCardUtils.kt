package com.example.pastyra.domain.usercase

import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.CardWithRank
import com.example.pastyra.domain.model.GameState
import com.example.pastyra.domain.model.Player

fun selectArithmeticTableCard(card: Card, state: GameState, playerId: Int): MutableList<Card> {
    val newTableActiveCard = state.game.tableActiveCards.toMutableList()
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
    val newTableActiveCard = state.game.tableActiveCards.toMutableList()
    val currentPlayer = state.game.playerList.find { it.playerId == playerId }

    if (card is CardWithRank && currentPlayer?.activeCard is CardWithRank && card.rank == currentPlayer.activeCard.rank) {
        newTableActiveCard.add(card)
    }

    return newTableActiveCard
}

fun selectCardForBundle(
    cardToBeBundled: Card,
    tableDeck: List<Card>,
    player: Player
): MutableList<Card> {
    val newTableActiveCard = tableDeck.toMutableList()

    if (cardToBeBundled !is CardWithRank) {
        newTableActiveCard.add(cardToBeBundled)
    }

    val cardVerificationToTake =
        isPlayerCanTakeAnyoneCard(player.cardToTheBundle as ArithmeticCard, newTableActiveCard)

    if (!cardVerificationToTake && newTableActiveCard.isNotEmpty()) {
        newTableActiveCard.removeLastOrNull()
    }

    return newTableActiveCard
}

fun removeTableCards(card: Card, chosenTableCards: List<Card>): List<Card> {
    val newChosenTableCards = chosenTableCards.toMutableList()


    return newChosenTableCards
}
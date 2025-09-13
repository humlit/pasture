package com.example.pastyra.domain.usercase

import com.example.pastyra.domain.model.Card
import isPlayerCanGetValueFromDeckForTakeCard

fun isPlayerCanTakeCard(deck: List<Card>, findCard: Card): Boolean {
    return when (findCard) {
        is Card.AnyArithmeticCard.ArithmeticCard -> isPlayerCanTakeArithmeticCard(deck, findCard)
        is Card.KingCard -> isPlayerCanTakeKing(deck)
        is Card.QueenCard -> isPlayerCanTakeQueen(deck)
        is Card.JackCard -> isPlayerCanTakeJackCard(deck)
        else -> throw IllegalArgumentException()
    }
}

fun isPlayerCanTakeArithmeticCard(
    deck: List<Card>, findCard: Card.AnyArithmeticCard.ArithmeticCard
): Boolean {
    return isPlayerCanGetValueFromDeckForTakeCard(deck, findCard.arithmeticValue)
}

fun isPlayerCanTakeKing(deck: List<Card>): Boolean {
    return deck.any { it is Card.KingCard }
}

fun isPlayerCanTakeQueen(deck: List<Card>): Boolean {
    return deck.any { it is Card.QueenCard }
}

fun isPlayerCanTakeJackCard(deck: List<Card>): Boolean {
    return deck.any { it is Card.AnyArithmeticCard.ArithmeticCard || it is Card.JackCard }
}
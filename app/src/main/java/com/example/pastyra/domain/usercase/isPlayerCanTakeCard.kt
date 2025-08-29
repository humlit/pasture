package com.example.pastyra.domain.usercase

import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.JackCard
import com.example.pastyra.domain.model.KingCard
import com.example.pastyra.domain.model.QueenCard
import isPlayerCanGetValueFromDeck

fun isPlayerCanTakeCard(deck: List<Card>, findCard: Card): Boolean {
    return when (findCard) {
        is ArithmeticCard -> isPlayerCanTakeArithmeticCard(deck, findCard)
        is KingCard -> isPlayerCanTakeKing(deck)
        is QueenCard -> isPlayerCanTakeQueen(deck)
        is JackCard -> isPlayerCanTakeJackCard(deck)
        else -> throw IllegalArgumentException()
    }
}

fun isPlayerCanTakeArithmeticCard(deck: List<Card>, findCard: ArithmeticCard): Boolean {
    return isPlayerCanGetValueFromDeck(deck, findCard.arithmeticValue)
}

fun isPlayerCanTakeKing(deck: List<Card>): Boolean {
    return deck.any { it is KingCard }
}

fun isPlayerCanTakeQueen(deck: List<Card>): Boolean {
    return deck.any { it is QueenCard }
}

fun isPlayerCanTakeJackCard(deck: List<Card>): Boolean {
    return deck.any { it is ArithmeticCard || it is JackCard }
}
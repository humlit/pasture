package com.example.pastyra.domain.usercase

import com.example.pastyra.domain.model.Card
import isPlayerCanGetValueFromFilteredDeck

fun isPlayerCanFinishTakingCard(
    tableActiveCards: List<Card>,
    temporaryPlayerTakenCards: List<Card>,
    tableDeck: List<Card>,
    cardThatTakes: Card,
): Boolean =
    tableActiveCards.isEmpty() &&
        temporaryPlayerTakenCards.isNotEmpty() &&
        !isPlayerCanTakeCard(
            tableDeck - temporaryPlayerTakenCards,
            tableActiveCards,
            cardThatTakes,
        )

fun isPlayerCanTakeCard(
    deck: List<Card>,
    tableActiveCards: List<Card>,
    findCard: Card,
): Boolean =
    when (findCard) {
        is Card.AnyArithmeticCard.ArithmeticCard ->
            isPlayerCanTakeArithmeticCard(
                deck,
                tableActiveCards,
                findCard,
            )

        is Card.KingCard -> isPlayerCanTakeKing(deck)
        is Card.QueenCard -> isPlayerCanTakeQueen(deck)
        is Card.JackCard -> isPlayerCanTakeJackCard(deck)
        else -> throw IllegalArgumentException()
    }

fun isPlayerCanTakeArithmeticCard(
    deck: List<Card>,
    tableActiveCards: List<Card>,
    findCard: Card.AnyArithmeticCard.ArithmeticCard,
): Boolean = isPlayerCanGetValueFromFilteredDeck(deck, tableActiveCards, findCard.arithmeticValue)

fun isPlayerCanTakeKing(deck: List<Card>): Boolean = deck.any { it is Card.KingCard }

fun isPlayerCanTakeQueen(deck: List<Card>): Boolean = deck.any { it is Card.QueenCard }

fun isPlayerCanTakeJackCard(deck: List<Card>): Boolean = deck.any { it is Card.AnyArithmeticCard.ArithmeticCard || it is Card.JackCard }

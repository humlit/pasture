package com.example.pastyra.domain.usercase

import com.example.pastyra.domain.model.Card

fun isPlayerCanTakeAnyoneCard(
    checkedCard: Card.AnyArithmeticCard.ArithmeticCard,
    tableActiveCards: List<Card.AnyArithmeticCard>,
): Boolean {
    val tableSum = tableActiveCards.sumOf { it.arithmeticValue }

    return tableSum <= checkedCard.arithmeticValue
}

fun activeTableCardEqualsPlayerCard(
    checkedCard: Card.AnyArithmeticCard.ArithmeticCard,
    tableActiveCard: List<Card>,
): Boolean {
    val tableSum =
        tableActiveCard.filterIsInstance<Card.AnyArithmeticCard>().sumOf { it.arithmeticValue }

    return tableSum == checkedCard.arithmeticValue
}

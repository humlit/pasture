package com.example.pastyra.domain.usercase

import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.domain.model.Card

fun isPlayerCanTakeAnyoneCard(
    playerActiveCard: ArithmeticCard,
    tableActiveCard: List<Card>
): Boolean {
        val tableSum = tableActiveCard
            .filterIsInstance<ArithmeticCard>()
            .sumOf { it.arithmeticValue }

        return tableSum <= playerActiveCard.arithmeticValue
}

fun activeTableCardEqualsPlayerCard(
    playerActiveCard: ArithmeticCard, tableActiveCard: List<Card>
): Boolean {

    val tableSum = tableActiveCard
        .filterIsInstance<ArithmeticCard>()
        .sumOf { it.arithmeticValue }

    return tableSum == playerActiveCard.arithmeticValue
}

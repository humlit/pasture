package com.example.pastyra.domain.usercase

import com.example.pastyra.domain.model.AnyArithmeticCard
import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.domain.model.Card

fun isPlayerCanTakeAnyoneCard(
    checkedCard: ArithmeticCard,
    tableActiveCard: List<Card>
): Boolean {
        val tableSum = tableActiveCard
            .filterIsInstance<AnyArithmeticCard>()
            .sumOf { it.arithmeticValue }

        return tableSum <= checkedCard.arithmeticValue
}

fun activeTableCardEqualsPlayerCard(
    checkedCard: ArithmeticCard, tableActiveCard: List<Card>
): Boolean {

    val tableSum = tableActiveCard
        .filterIsInstance<AnyArithmeticCard>()
        .sumOf { it.arithmeticValue }

    return tableSum == checkedCard.arithmeticValue
}

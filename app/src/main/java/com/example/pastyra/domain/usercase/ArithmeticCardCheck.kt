package com.example.pastyra.domain.usercase

import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.domain.model.Card

fun isSelectable(playerActiveCard: Card?, tableActiveCard: List<Card>): Boolean {

    if (playerActiveCard !is ArithmeticCard) return false

    val tableSum = tableActiveCard
        .filterIsInstance<ArithmeticCard>()
        .sumOf { it.arithmeticValue }

    return tableSum <= playerActiveCard.arithmeticValue
}

fun arithmeticTableWatcher(playerActiveCard: Card?, tableActiveCard: List<Card>) {

}
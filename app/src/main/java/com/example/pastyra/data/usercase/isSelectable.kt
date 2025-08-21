package com.example.pastyra.data.usercase

import com.example.pastyra.domain.model.Card

fun isSelectable(playerActiveCard: Card, tableDeckActiveCard: List<Card>): Boolean {
    val isCanTake = tableDeckActiveCard.sumBy {
        it.arithmeticValue ?: 0
    } > playerActiveCard.arithmeticValue!!

    return isCanTake
}
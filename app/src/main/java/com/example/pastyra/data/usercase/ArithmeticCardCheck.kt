package com.example.pastyra.data.usercase

import com.example.pastyra.domain.model.Card

fun isSelectable(playerActiveCard: Card?, tableActiveCard: List<Card>): Boolean {

    if (playerActiveCard?.arithmeticValue == null || tableActiveCard.sumBy { it.arithmeticValue!! } > playerActiveCard.arithmeticValue) {
        return false
    }

    return true
}


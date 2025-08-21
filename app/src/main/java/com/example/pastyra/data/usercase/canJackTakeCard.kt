package com.example.pastyra.data.usercase

import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.Rank

fun canJackTakeCard(tableDeck: List<Card>): Boolean {
    return tableDeck.isNotEmpty() || tableDeck.any { it.rank == Rank.JACK } || tableDeck.sumBy {
        it.arithmeticValue ?: 0
    } != 0
}

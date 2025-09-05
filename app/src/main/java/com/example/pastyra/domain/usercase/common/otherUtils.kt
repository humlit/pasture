package com.example.pastyra.domain.usercase.common

import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.safeSuit
import com.example.pastyra.domain.model.safeValue

fun List<Card>.sortedCard(): List<Card> {
    return this.sortedWith(compareBy<Card> { it.safeSuit }.thenBy { it.safeValue })
}
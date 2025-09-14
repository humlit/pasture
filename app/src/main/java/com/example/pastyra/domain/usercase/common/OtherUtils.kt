package com.example.pastyra.domain.usercase.common

import com.example.pastyra.domain.model.ArithmeticCardInterface
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.SimpleCard

fun List<Card>.sortedCard(): List<Card> =
    this.sortedWith(
        compareBy<Card> {
            (it as? SimpleCard)?.suit
        }.thenBy {
            (it as? ArithmeticCardInterface)?.arithmeticValue ?: 0
        },
    )

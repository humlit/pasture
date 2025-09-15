package com.example.pastyra.domain.usercase.bundlingTests

import canPlayerFinishBundlingCards
import com.example.pastyra.domain.model.CARDS
import com.example.pastyra.domain.model.Card
import org.junit.Assert
import org.junit.Test

class CanPlayerFinishBundlingCardsTests {
    @Test
    fun should_work_correct_test1() {
        val tableDeck =
            listOf<Card>(
                CARDS.FIVE.SPADES,
            )

        val tableActiveCards = listOf<Card>(CARDS.FOUR.CLUBS)

        val cardToBeBundled = CARDS.FIVE.DIAMONDS

        val canPlayerFinishBundling =
            canPlayerFinishBundlingCards(
                cardToBeBundled,
                tableDeck,
                tableActiveCards,
            )

        Assert.assertEquals(
            "Игрок не может закончить завязку, если значение связки не соответсвтует карте",
            canPlayerFinishBundling,
            false,
        )
    }

    @Test
    fun should_work_correct_test2() {
        val tableDeck =
            listOf<Card>(
                CARDS.FIVE.SPADES,
            )

        val tableActiveCards = listOf<Card>()

        val cardToBeBundled = CARDS.FIVE.DIAMONDS

        val canPlayerFinishBundling =
            canPlayerFinishBundlingCards(
                cardToBeBundled,
                tableDeck,
                tableActiveCards,
            )

        Assert.assertEquals(
            "Игрок не может закончить завязку, если еще есть карты для завязки",
            canPlayerFinishBundling,
            false,
        )
    }

    @Test
    fun should_work_correct_test3() {
        val tableDeck =
            listOf<Card>(
                CARDS.SIX.SPADES,
            )

        val tableActiveCards = listOf<Card>()

        val cardToBeBundled = CARDS.FIVE.DIAMONDS

        val canPlayerFinishBundling =
            canPlayerFinishBundlingCards(
                cardToBeBundled,
                tableDeck,
                tableActiveCards,
            )

        Assert.assertEquals(
            "Игрок может закончить завязку, если больше нечего завязывать",
            canPlayerFinishBundling,
            true,
        )
    }
}

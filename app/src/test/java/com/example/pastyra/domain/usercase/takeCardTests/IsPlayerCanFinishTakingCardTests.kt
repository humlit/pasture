package com.example.pastyra.domain.usercase.takeCardTests

import com.example.pastyra.domain.model.CARDS
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.usercase.isPlayerCanFinishTakingCard
import org.junit.Assert
import org.junit.Test

class IsPlayerCanFinishTakingCardTests {
    @Test
    fun isPlayerCanFinishTakingCard_test1() {
        listOf<Card>(
            CARDS.EIGHT.HEARTS,
        )

        val tableDeck =
            listOf<Card>(
                CARDS.TWO.DIAMONDS,
                CARDS.SIX.DIAMONDS,
                CARDS.EIGHT.DIAMONDS,
            )

        val tableActiveCards = listOf<Card>()

        val temporaryPlayerTakenCards =
            listOf<Card>(
                CARDS.EIGHT.DIAMONDS,
            )

        val playerCanFinishTakingCard =
            isPlayerCanFinishTakingCard(
                tableActiveCards = tableActiveCards,
                temporaryPlayerTakenCards = temporaryPlayerTakenCards,
                tableDeck = tableDeck,
                cardThatTakes = CARDS.EIGHT.HEARTS,
            )

        val expected = false

        Assert.assertEquals(
            "Не можем завершить взятку, пока еще есть что взять со стола",
            expected,
            playerCanFinishTakingCard,
        )
    }
}

package com.example.pastyra.domain.usercase.takeCardTests

import com.example.pastyra.domain.model.CARDS
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.usercase.isPlayerCanTakeCard
import org.junit.Assert
import org.junit.Test
import java.util.UUID

class IsPlayerCanTakeCardTests {
    @Test
    fun isPlayerCanTakeCard_test1() {
        val playerHand =
            listOf<Card>(
                CARDS.EIGHT.HEARTS,
            )

        val tableCards =
            listOf<Card>(
                CARDS.TWO.DIAMONDS,
                Card.AnyArithmeticCard.BundleCard(
                    cardList =
                        listOf(
                            CARDS.FIVE.DIAMONDS,
                            CARDS.ACE.CLUBS,
                        ),
                    playerId = UUID.randomUUID(),
                    arithmeticValue = 6,
                ),
            )

        val playerCanTakeCard =
            isPlayerCanTakeCard(
                deck = tableCards,
                tableActiveCards = emptyList(),
                findCard = playerHand[0],
            )

        val expected = false

        Assert.assertEquals(
            "Не можем ничего взять со стола",
            expected,
            playerCanTakeCard,
        )
    }

    @Test
    fun isPlayerCanTakeCard_test2() {
        val playerHand =
            listOf<Card>(
                CARDS.EIGHT.HEARTS,
            )

        val tableCards =
            listOf<Card>(
                CARDS.TWO.DIAMONDS,
                CARDS.ACE.CLUBS,
                CARDS.FIVE.SPADES,
                Card.AnyArithmeticCard.BundleCard(
                    cardList =
                        listOf(
                            CARDS.FIVE.DIAMONDS,
                            CARDS.ACE.CLUBS,
                        ),
                    playerId = UUID.randomUUID(),
                    arithmeticValue = 6,
                ),
            )

        val playerCanTakeCard =
            isPlayerCanTakeCard(
                deck = tableCards,
                tableActiveCards = emptyList(),
                findCard = playerHand[0],
            )

        val expected = true

        Assert.assertEquals(
            "Не можем ничего взять со стола",
            expected,
            playerCanTakeCard,
        )
    }
}

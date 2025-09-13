package com.example.pastyra.domain.usercase.bundlingTests

import canPlayerFinishBundlingCards
import com.example.pastyra.domain.model.CARDS
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.Player
import org.junit.Assert
import org.junit.Test

class CanPlayerFinishBundlingCardsTests {
    @Test
    fun should_work_correct_test1() {
        val playerHand = listOf<Card>(
            CARDS.FIVE.DIAMONDS,
            CARDS.ACE.CLUBS,
        )

        val tableDeck = listOf<Card>(
            CARDS.FIVE.SPADES,
            CARDS.FOUR.CLUBS,
        )

        val cardThatBundles = CARDS.ACE.CLUBS
        val cardToBeBundled = CARDS.FIVE.DIAMONDS

        Player(playerId = 1, activeCard = cardThatBundles, cardsHand = playerHand)

        val temporaryBundlingDeck = listOf<List<Card>>(
            listOf(CARDS.FOUR.CLUBS, cardThatBundles)
        )

        val canPlayerFinishBundling =
            canPlayerFinishBundlingCards(cardToBeBundled, tableDeck, temporaryBundlingDeck)

        Assert.assertEquals(
            "Игрок не может закончить завязку, если еще есть карты для завязки",
            canPlayerFinishBundling,
            false
        )
    }
}
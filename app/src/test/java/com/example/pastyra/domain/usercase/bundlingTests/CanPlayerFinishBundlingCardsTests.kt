package com.example.pastyra.domain.usercase.bundlingTests

import canPlayerFinishBundlingCards
import com.example.pastyra.domain.model.BundleCard
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

        val tableActiveCards = emptyList<Card>()

        val cardThatBundles = CARDS.ACE.CLUBS
        val cardToBeBundled = CARDS.FIVE.DIAMONDS

        val player = Player(playerId = 1, activeCard = cardThatBundles, cardsHand = playerHand)

        val temporaryBundlingDeck = listOf<BundleCard>(
            BundleCard(
                cardList = listOf<Card>(
                    CARDS.FOUR.CLUBS,
                    cardThatBundles
                ), //TODO: Этот параметр нам не нужен, но я оставил пока что его тут
                point = 0,
                arithmeticValue = 5,
                playerId = player.playerId,
            )
        )

        val canPlayerFinishBundling =
            canPlayerFinishBundlingCards(
                cardToBeBundled,
                tableDeck,
                tableActiveCards,
            )

        Assert.assertEquals(
            "Игрок не может закончить завязку, если еще есть карты для завязки",
            canPlayerFinishBundling,
            false
        )
    }
}
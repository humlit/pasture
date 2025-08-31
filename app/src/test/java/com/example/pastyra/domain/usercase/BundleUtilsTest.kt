package com.example.pastyra

import com.example.pastyra.domain.model.BundleCard
import com.example.pastyra.domain.model.CARDS
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.Player
import getPlayerBundlingCards
import org.junit.Assert.assertEquals
import org.junit.Test

class BundleUtilsTest {
    @Test
    fun getPlayerBundlingCards_should_work_correct_test1() {
        val playerHand = listOf<Card>(
            CARDS.FIVE.DIAMONDS,
            CARDS.ACE.CLUBS,
            CARDS.TWO.CLUBS,
            CARDS.JACK.HEARTS,
        )

        val tableDeck = listOf<Card>(
            CARDS.ACE.HEARTS,
            CARDS.QUEEN.CLUBS,
            CARDS.NINE.SPADES,
            CARDS.NINE.CLUBS,
        )


        val activePlayerCard = CARDS.TWO.CLUBS

        val player = Player(playerId = 1, activeCard = activePlayerCard, cardsHand = playerHand)

        val playerBundlingCards = getPlayerBundlingCards(player, tableDeck)

        val expectedPlayerBundlingCards = listOf<Card>()

        assertEquals(
            "Карты, доступные для завязки должны соответствовать ожидаемым",
            expectedPlayerBundlingCards,
            playerBundlingCards
        )
    }

    @Test
    fun getPlayerBundlingCards_should_work_correct_test2() {
        val playerHand = listOf<Card>(
            CARDS.THREE.DIAMONDS,
            CARDS.TWO.SPADES,
            CARDS.ACE.HEARTS,
            CARDS.ACE.DIAMONDS,
        )

        val tableDeck = listOf<Card>(
            CARDS.NINE.DIAMONDS,
            CARDS.QUEEN.SPADES,
            CARDS.EIGHT.HEARTS,
            CARDS.TWO.HEARTS,
            CARDS.SEVEN.DIAMONDS,
        )

        val activePlayerCard = CARDS.ACE.HEARTS

        val player = Player(playerId = 1, activeCard = activePlayerCard, cardsHand = playerHand)

        val playerBundlingCards = getPlayerBundlingCards(player, tableDeck)

        val expectedPlayerBundlingCards = listOf<Card>(
            CARDS.THREE.DIAMONDS
        )

        assertEquals(
            "Карты, доступные для завязки должны соответствовать ожидаемым",
            expectedPlayerBundlingCards,
            playerBundlingCards
        )
    }

    @Test
    fun getPlayerBundlingCards_should_work_correct_test3() {
        val playerHand = listOf<Card>(
            CARDS.EIGHT.DIAMONDS,
            CARDS.EIGHT.CLUBS,
            CARDS.EIGHT.HEARTS,
            CARDS.JACK.HEARTS,
        )

        val tableDeck = listOf<Card>(
            CARDS.EIGHT.SPADES,
        )

        val activePlayerCard = CARDS.EIGHT.HEARTS

        val player = Player(playerId = 1, activeCard = activePlayerCard, cardsHand = playerHand)

        val playerBundlingCards = getPlayerBundlingCards(player, tableDeck)

        val expectedPlayerBundlingCards = listOf<Card>(
            CARDS.EIGHT.DIAMONDS,
            CARDS.EIGHT.CLUBS,
        )

        assertEquals(
            "Можно повысить уровень завязки, используя карту с таким же значением",
            expectedPlayerBundlingCards,
            playerBundlingCards
        )
    }

    @Test
    fun getPlayerBundlingCards_should_work_correct_test4() {
        val playerHand = listOf<Card>(
            CARDS.EIGHT.DIAMONDS, CARDS.TWO.DIAMONDS
        )

        val tableDeck = listOf<Card>(
            BundleCard(
                cardList = listOf<Card>(
                    CARDS.ACE.DIAMONDS,
                    CARDS.FIVE.DIAMONDS,
                ),
                point = 1,
                arithmeticValue = 6,
                playerId = 1,
            ), CARDS.TEN.DIAMONDS
        )

        val activePlayerCard = CARDS.TWO.DIAMONDS

        val player = Player(playerId = 1, activeCard = activePlayerCard, cardsHand = playerHand)

        val playerBundlingCards = getPlayerBundlingCards(player, tableDeck)

        val expectedPlayerBundlingCards = listOf<Card>(
            CARDS.EIGHT.DIAMONDS,
        )

        assertEquals(
            "Должна быть возможность перевязать завязку 1 уровня со стола",
            expectedPlayerBundlingCards,
            playerBundlingCards
        )
    }

    @Test
    fun getPlayerBundlingCards_should_work_correct_test5() {
        val playerHand = listOf<Card>(
            CARDS.EIGHT.DIAMONDS, CARDS.TWO.DIAMONDS
        )

        val tableDeck = listOf<Card>(
            BundleCard(
                cardList = listOf<Card>(
                    CARDS.ACE.DIAMONDS,
                    CARDS.FIVE.DIAMONDS,
                    CARDS.SIX.DIAMONDS,
                ), point = 1, arithmeticValue = 6, playerId = 1, bundleStage = 2
            )
        )

        val activePlayerCard = CARDS.TWO.DIAMONDS

        val player = Player(playerId = 1, activeCard = activePlayerCard, cardsHand = playerHand)

        val playerBundlingCards = getPlayerBundlingCards(player, tableDeck)

        val expectedPlayerBundlingCards = listOf<Card>()

        assertEquals(
            "Не должна быть возможность перевязать завязку 2 уровня со стола",
            expectedPlayerBundlingCards,
            playerBundlingCards
        )
    }

    @Test
    fun getPlayerBundlingCards_should_work_correct_test6() {
        val playerHand = listOf<Card>(
            CARDS.EIGHT.DIAMONDS, CARDS.TWO.DIAMONDS
        )

        val tableDeck = listOf<Card>(
            BundleCard(
                cardList = listOf<Card>(
                    CARDS.ACE.DIAMONDS,
                    CARDS.FOUR.DIAMONDS,
                ),
                point = 1,
                arithmeticValue = 5,
                playerId = 2,
            ), CARDS.ACE.HEARTS
        )

        val activePlayerCard = CARDS.TWO.DIAMONDS

        val player = Player(playerId = 1, activeCard = activePlayerCard, cardsHand = playerHand)

        val playerBundlingCards = getPlayerBundlingCards(player, tableDeck)

        val expectedPlayerBundlingCards = listOf<Card>(
            CARDS.EIGHT.DIAMONDS,
        )

        assertEquals(
            "Должна быть возможность перевязать завязку 1 уровня с использованием дополнительной карты со стола",
            expectedPlayerBundlingCards,
            playerBundlingCards
        )
    }
  
    @Test
    fun getPlayerBundlingCards_should_work_correct_test7() {
        val playerHand = listOf<Card>(
            CARDS.EIGHT.DIAMONDS, CARDS.TWO.DIAMONDS
        )

        val tableDeck = listOf<Card>(
            BundleCard(
                cardList = listOf<Card>(
                    CARDS.ACE.DIAMONDS,
                    CARDS.FOUR.DIAMONDS,
                ),
                point = 1,
                arithmeticValue = 5,
                playerId = 2,
            ), CARDS.TWO.HEARTS
        )

        val activePlayerCard = CARDS.TWO.DIAMONDS

        val player = Player(playerId = 1, activeCard = activePlayerCard, cardsHand = playerHand)

        val playerBundlingCards = getPlayerBundlingCards(player, tableDeck)

        val expectedPlayerBundlingCards = listOf<Card>()

        assertEquals(
            "Нет возможности перевязать завязку 1 уровня с использованием дополнительной карты со стола, если сумма неправильная",
            expectedPlayerBundlingCards,
            playerBundlingCards
        )
    }
}
package com.example.pastyra.domain.model

import java.util.UUID

fun getArithmeticCardPoints(
    rank: Rank,
    suit: Suit,
): Int =
    when {
        rank == Rank.ACE -> 1
        rank == Rank.TWO && suit == Suit.CLUBS -> 2
        rank == Rank.TEN && suit == Suit.DIAMONDS -> 3

        else -> 0
    }

fun calcBundlePoints(cards: List<Card.AnyArithmeticCard>) = cards.sumOf { it.point }

interface SimpleCard {
    val rank: Rank

    val suit: Suit

    val point: Int

    val icon: Int
}

interface ArithmeticCardInterface {
    val arithmeticValue: Int

    val point: Int
}

sealed class Card(
    open val point: Int = 0,
    open val id: UUID = UUID.randomUUID(),
) {
    data class KingCard(
        override val suit: Suit,
        override val icon: Int,
        override val rank: Rank = Rank.KING,
    ) : Card(),
        SimpleCard

    data class QueenCard(
        override val rank: Rank = Rank.QUEEN,
        override val suit: Suit,
        override val icon: Int,
    ) : Card(),
        SimpleCard

    data class JackCard(
        override val rank: Rank = Rank.JACK,
        override val suit: Suit,
        override val icon: Int,
    ) : Card(
            point = 1,
        ),
        SimpleCard

    sealed class AnyArithmeticCard(
        override val point: Int,
    ) : Card(),
        ArithmeticCardInterface {
        data class ArithmeticCard(
            override val rank: Rank,
            override val suit: Suit,
            override val arithmeticValue: Int,
            override val icon: Int,
        ) : AnyArithmeticCard(point = getArithmeticCardPoints(rank, suit)),
            SimpleCard,
            ArithmeticCardInterface

        data class BundleCard(
            val cardList: List<ArithmeticCard>,
            val playerId: UUID,
            val bundleStage: Int = 1,
            override val arithmeticValue: Int,
        ) : AnyArithmeticCard(point = calcBundlePoints(cardList)),
            ArithmeticCardInterface
    }
}

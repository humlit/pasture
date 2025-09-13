package com.example.pastyra.domain.model

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
    open val id: Int = 0,
) {
    data class KingCard(
        override val suit: Suit,
        override val icon: Int,
        override val rank: Rank = Rank.KING,
        override val point: Int = 0,
        override val id: Int,
    ) : Card(
        point,
        id = id,
    ), SimpleCard

    data class JackCard(
        override val rank: Rank = Rank.JACK,
        override val suit: Suit,
        override val icon: Int,
        override val point: Int = 1,
        override val id: Int,
    ) : Card(
        point = point,
        id = id,
    ), SimpleCard

    data class QueenCard(
        override val rank: Rank = Rank.QUEEN,
        override val suit: Suit,
        override val icon: Int,
        override val point: Int = 0,
        override val id: Int,
    ) : Card(
        point,
        id = id,
    ), SimpleCard

    sealed class AnyArithmeticCard(
        override val point: Int = 0,
        override val id: Int,
    ) : Card(
        point,
        id = id,
    ), ArithmeticCardInterface {
        data class ArithmeticCard(
            override val rank: Rank,
            override val suit: Suit,
            override val arithmeticValue: Int,
            override val point: Int = 0,
            override val id: Int,
            override val icon: Int,
        ) : AnyArithmeticCard(point = point, id = id), SimpleCard, ArithmeticCardInterface

        data class BundleCard(
            // TODO: Должно быть просто ArithmeticCard
            val cardList: List<AnyArithmeticCard>,
            val playerId: Int,
            val bundleStage: Int = 1,
            override val arithmeticValue: Int,
            override val point: Int,
            // TODO: BundleCard тоже должен иметь id уникальный
        ) : AnyArithmeticCard(point = point, id = 0), ArithmeticCardInterface
    }
}

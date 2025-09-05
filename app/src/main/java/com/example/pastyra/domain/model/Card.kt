package com.example.pastyra.domain.model

open class Card(
    open val point: Int = 0,
    open val id: Int = 0,

    ) {

}

interface CardWithRank {
    val rank: Rank
}

interface CardBack {
    val icon: Int
}

interface AnyArithmeticCard {
    val arithmeticValue: Int
}

class ArithmeticCard(
    val rank: Rank,
    val suit: Suit,
    override val point: Int = 0,
    override val arithmeticValue: Int,
    override val id: Int,
    override val icon: Int,
) : Card(
    point = point,
    id = id,
), CardBack, AnyArithmeticCard {

}

class QueenCard(
    override val rank: Rank = Rank.QUEEN,
    val suit: Suit,
    override val point: Int = 0,
    override val id: Int,
    override val icon: Int
) : Card(
    point,
    id = id,
), CardWithRank, CardBack {

}

class KingCard(
    override val rank: Rank = Rank.KING,
    val suit: Suit,
    override val point: Int = 0,
    override val id: Int,
    override val icon: Int
) : Card(
    point,
    id = id,
), CardWithRank, CardBack {

}

class JackCard(
    override val rank: Rank = Rank.JACK,
    val suit: Suit,
    override val point: Int = 1,
    override val id: Int,
    override val icon: Int
) : Card(
    point = point,
    id = id,
), CardWithRank, CardBack {

}

class BundleCard(
    val cardList: List<Card>,
    override val point: Int,
    override val arithmeticValue: Int,
    val playerId: Int,
    val bundleStage: Int = 1,
) : Card(
    point = point,
), AnyArithmeticCard {

}

val Card.safeRank: Rank?
    get() = when (this) {
        is ArithmeticCard -> this.rank
        is QueenCard -> this.rank
        is KingCard -> this.rank
        is JackCard -> this.rank
        else -> null
    }

val Card.safeSuit: Suit?
    get() = when (this) {
        is ArithmeticCard -> this.suit
        is QueenCard -> this.suit
        is KingCard -> this.suit
        is JackCard -> this.suit
        else -> null
    }

val Card.safeValue: Int
    get() = when (this) {
        is ArithmeticCard -> this.arithmeticValue
        else -> 0
    }
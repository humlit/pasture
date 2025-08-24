package com.example.pastyra.domain.model

open class Card(
    open val point: Int = 0,
    open val id: Int = 0,
) {

}

interface CardWithRank {
    val rank: Rank
}

class ArithmeticCard(
    val rank: Rank,
    val suit: Suit,
    override val point: Int = 0,
    val arithmeticValue: Int,
    override val id: Int,
) : Card(
    point = point,
    id = id,
) {

}

class QueenCard(
    override val rank: Rank = Rank.QUEEN,
    val suit: Suit,
    override val point: Int = 0,
    override val id: Int,
) : Card(
    point,
    id = id,
), CardWithRank {

}

class KingCard(
    override val rank: Rank = Rank.KING,
    val suit: Suit,
    override val point: Int = 0,
    override val id: Int,
) : Card(
    point,
    id = id,
), CardWithRank {

}

class JackCard(
    override val rank: Rank = Rank.JACK,
    val suit: Suit,
    override val point: Int = 1,
    override val id: Int,
) : Card(
    point = point,
    id = id,
), CardWithRank {

}

class BundleCard(
    override val point: Int,
    val arithmeticValue: Int,
    val playerId: Int,
) : Card(
    point = point,
) {

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

package com.example.pastyra.domain.model

enum class Suit {
    HEARTS, DIAMONDS, CLUBS, SPADES
}

enum class Rank {
    ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
}

val DECK: MutableList<Card> = mutableListOf(
    ArithmeticCard(Rank.ACE, Suit.DIAMONDS, point = 1, arithmeticValue = 1, id = 1),
    ArithmeticCard(Rank.ACE, Suit.CLUBS, point = 1, arithmeticValue = 1, id = 2),
    ArithmeticCard(Rank.ACE, Suit.SPADES, point = 1, arithmeticValue = 1, id = 3),
    ArithmeticCard(Rank.ACE, Suit.HEARTS, point = 1, arithmeticValue = 1, id = 4),

    ArithmeticCard(Rank.TWO, Suit.DIAMONDS, arithmeticValue = 2, id = 5),
    ArithmeticCard(Rank.TWO, Suit.CLUBS, point = 2, arithmeticValue = 2, id = 6),
    ArithmeticCard(Rank.TWO, Suit.SPADES, arithmeticValue = 2, id = 7),
    ArithmeticCard(Rank.TWO, Suit.HEARTS, arithmeticValue = 2, id = 8),

    ArithmeticCard(Rank.THREE, Suit.DIAMONDS, arithmeticValue = 3, id = 9),
    ArithmeticCard(Rank.THREE, Suit.CLUBS, arithmeticValue = 3, id = 10),
    ArithmeticCard(Rank.THREE, Suit.SPADES, arithmeticValue = 3, id = 11),
    ArithmeticCard(Rank.THREE, Suit.HEARTS, arithmeticValue = 3, id = 12),

    ArithmeticCard(Rank.FOUR, Suit.DIAMONDS, arithmeticValue = 4, id = 13),
    ArithmeticCard(Rank.FOUR, Suit.CLUBS, arithmeticValue = 4, id = 14),
    ArithmeticCard(Rank.FOUR, Suit.SPADES, arithmeticValue = 4, id = 15),
    ArithmeticCard(Rank.FOUR, Suit.HEARTS, arithmeticValue = 4, id = 16),

    ArithmeticCard(Rank.FIVE, Suit.DIAMONDS, arithmeticValue = 5, id = 17),
    ArithmeticCard(Rank.FIVE, Suit.CLUBS, arithmeticValue = 5, id = 18),
    ArithmeticCard(Rank.FIVE, Suit.SPADES, arithmeticValue = 5, id = 19),
    ArithmeticCard(Rank.FIVE, Suit.HEARTS, arithmeticValue = 5, id = 20),

    ArithmeticCard(Rank.SIX, Suit.DIAMONDS, arithmeticValue = 6, id = 21),
    ArithmeticCard(Rank.SIX, Suit.CLUBS, arithmeticValue = 6, id = 22),
    ArithmeticCard(Rank.SIX, Suit.SPADES, arithmeticValue = 6, id = 23),
    ArithmeticCard(Rank.SIX, Suit.HEARTS, arithmeticValue = 6, id = 24),

    ArithmeticCard(Rank.SEVEN, Suit.DIAMONDS, arithmeticValue = 7, id = 25),
    ArithmeticCard(Rank.SEVEN, Suit.CLUBS, arithmeticValue = 7, id = 26),
    ArithmeticCard(Rank.SEVEN, Suit.SPADES, arithmeticValue = 7, id = 27),
    ArithmeticCard(Rank.SEVEN, Suit.HEARTS, arithmeticValue = 7, id = 28),

    ArithmeticCard(Rank.EIGHT, Suit.DIAMONDS, arithmeticValue = 8, id = 29),
    ArithmeticCard(Rank.EIGHT, Suit.CLUBS, arithmeticValue = 8, id = 30),
    ArithmeticCard(Rank.EIGHT, Suit.SPADES, arithmeticValue = 8, id = 31),
    ArithmeticCard(Rank.EIGHT, Suit.HEARTS, arithmeticValue = 8, id = 32),

    ArithmeticCard(Rank.NINE, Suit.DIAMONDS, arithmeticValue = 9, id = 33),
    ArithmeticCard(Rank.NINE, Suit.CLUBS, arithmeticValue = 9, id = 34),
    ArithmeticCard(Rank.NINE, Suit.SPADES, arithmeticValue = 9, id = 35),
    ArithmeticCard(Rank.NINE, Suit.HEARTS, arithmeticValue = 9, id = 36),

    ArithmeticCard(Rank.TEN, Suit.DIAMONDS, point = 3, arithmeticValue = 10, id = 37),
    ArithmeticCard(Rank.TEN, Suit.CLUBS, arithmeticValue = 10, id = 38),
    ArithmeticCard(Rank.TEN, Suit.SPADES, arithmeticValue = 10, id = 39),
    ArithmeticCard(Rank.TEN, Suit.HEARTS, arithmeticValue = 10, id = 40),

    JackCard(suit = Suit.DIAMONDS, id = 41),
    JackCard(suit = Suit.CLUBS, id = 42),
    JackCard(suit = Suit.SPADES, id = 43),
    JackCard(suit = Suit.HEARTS, id = 44),

    QueenCard(suit = Suit.DIAMONDS, id = 45),
    QueenCard(suit = Suit.CLUBS, id = 46),
    QueenCard(suit = Suit.SPADES, id = 47),
    QueenCard(suit = Suit.HEARTS, id = 48),

    KingCard(suit = Suit.DIAMONDS, id = 49),
    KingCard(suit = Suit.CLUBS, id = 50),
    KingCard(suit = Suit.SPADES, id = 51),
    KingCard(suit = Suit.HEARTS, id = 52),
).shuffled().toMutableList()

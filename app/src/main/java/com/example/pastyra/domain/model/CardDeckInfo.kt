package com.example.pastyra.domain.model

enum class Suit {
    HEARTS, DIAMONDS, CLUBS, SPADES
}

enum class Rank {
    ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
}

val DECK: MutableList<Card> = mutableListOf(
    Card(Rank.ACE, Suit.DIAMONDS, point = 1, arithmeticValue = 1, id = 1),
    Card(Rank.ACE, Suit.CLUBS, point = 1, arithmeticValue = 1, id = 2),
    Card(Rank.ACE, Suit.SPADES, point = 1, arithmeticValue = 1, id = 3),
    Card(Rank.ACE, Suit.HEARTS, point = 1, arithmeticValue = 1, id = 4),

    Card(Rank.TWO, Suit.DIAMONDS, arithmeticValue = 2, id = 5),
    Card(Rank.TWO, Suit.CLUBS, point = 2, arithmeticValue = 2, id = 6),
    Card(Rank.TWO, Suit.SPADES, arithmeticValue = 2, id = 7),
    Card(Rank.TWO, Suit.HEARTS, arithmeticValue = 2, id = 8),

    Card(Rank.THREE, Suit.DIAMONDS, arithmeticValue = 3, id = 9),
    Card(Rank.THREE, Suit.CLUBS, arithmeticValue = 3, id = 10),
    Card(Rank.THREE, Suit.SPADES, arithmeticValue = 3, id = 11),
    Card(Rank.THREE, Suit.HEARTS, arithmeticValue = 3, id = 12),

    Card(Rank.FOUR, Suit.DIAMONDS, arithmeticValue = 4, id = 13),
    Card(Rank.FOUR, Suit.CLUBS, arithmeticValue = 4, id = 14),
    Card(Rank.FOUR, Suit.SPADES, arithmeticValue = 4, id = 15),
    Card(Rank.FOUR, Suit.HEARTS, arithmeticValue = 4, id = 16),

    Card(Rank.FIVE, Suit.DIAMONDS, arithmeticValue = 5, id = 17),
    Card(Rank.FIVE, Suit.CLUBS, arithmeticValue = 5, id = 18),
    Card(Rank.FIVE, Suit.SPADES, arithmeticValue = 5, id = 19),
    Card(Rank.FIVE, Suit.HEARTS, arithmeticValue = 5, id = 20),

    Card(Rank.SIX, Suit.DIAMONDS, arithmeticValue = 6, id = 21),
    Card(Rank.SIX, Suit.CLUBS, arithmeticValue = 6, id = 22),
    Card(Rank.SIX, Suit.SPADES, arithmeticValue = 6, id = 23),
    Card(Rank.SIX, Suit.HEARTS, arithmeticValue = 6, id = 24),

    Card(Rank.SEVEN, Suit.DIAMONDS, arithmeticValue = 7, id = 25),
    Card(Rank.SEVEN, Suit.CLUBS, arithmeticValue = 7, id = 26),
    Card(Rank.SEVEN, Suit.SPADES, arithmeticValue = 7, id = 27),
    Card(Rank.SEVEN, Suit.HEARTS, arithmeticValue = 7, id = 28),

    Card(Rank.EIGHT, Suit.DIAMONDS, arithmeticValue = 8, id = 29),
    Card(Rank.EIGHT, Suit.CLUBS, arithmeticValue = 8, id = 30),
    Card(Rank.EIGHT, Suit.SPADES, arithmeticValue = 8, id = 31),
    Card(Rank.EIGHT, Suit.HEARTS, arithmeticValue = 8, id = 32),

    Card(Rank.NINE, Suit.DIAMONDS, arithmeticValue = 9, id = 33),
    Card(Rank.NINE, Suit.CLUBS, arithmeticValue = 9, id = 34),
    Card(Rank.NINE, Suit.SPADES, arithmeticValue = 9, id = 35),
    Card(Rank.NINE, Suit.HEARTS, arithmeticValue = 9, id = 36),

    Card(Rank.TEN, Suit.DIAMONDS, point = 3, arithmeticValue = 10, id = 37),
    Card(Rank.TEN, Suit.CLUBS, arithmeticValue = 10, id = 38),
    Card(Rank.TEN, Suit.SPADES, arithmeticValue = 10, id = 39),
    Card(Rank.TEN, Suit.HEARTS, arithmeticValue = 10, id = 40),

    Card(Rank.JACK, Suit.DIAMONDS, point = 1, id = 41),
    Card(Rank.JACK, Suit.CLUBS, point = 1, id = 42),
    Card(Rank.JACK, Suit.SPADES, point = 1, id = 43),
    Card(Rank.JACK, Suit.HEARTS, point = 1, id = 44),

    Card(Rank.QUEEN, Suit.DIAMONDS, canJackPickUp = false, id = 45),
    Card(Rank.QUEEN, Suit.CLUBS, canJackPickUp = false, id = 46),
    Card(Rank.QUEEN, Suit.SPADES, canJackPickUp = false, id = 47),
    Card(Rank.QUEEN, Suit.HEARTS, canJackPickUp = false, id = 48),

    Card(Rank.KING, Suit.DIAMONDS, canJackPickUp = false, id = 49),
    Card(Rank.KING, Suit.CLUBS, canJackPickUp = false, id = 50),
    Card(Rank.KING, Suit.SPADES, canJackPickUp = false, id = 51),
    Card(Rank.KING, Suit.HEARTS, canJackPickUp = false, id = 52),
).shuffled().toMutableList()

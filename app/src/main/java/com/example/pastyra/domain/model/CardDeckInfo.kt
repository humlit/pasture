package com.example.pastyra.domain.model

import com.example.pastyra.R

enum class Suit {
    HEARTS, DIAMONDS, CLUBS, SPADES
}

enum class Rank {
    ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
}

object CARDS {
    object ACE {
        val DIAMONDS = ArithmeticCard(
            Rank.ACE,
            Suit.DIAMONDS,
            point = 1,
            arithmeticValue = 1,
            id = 3,
            icon = R.drawable.ic_ace_of_diamonds,
        )

        val CLUBS = ArithmeticCard(
            Rank.ACE,
            Suit.CLUBS,
            point = 1,
            arithmeticValue = 1,
            id = 3,
            icon = R.drawable.ic_ace_of_clubs,
        )

        val SPADES = ArithmeticCard(
            Rank.ACE,
            Suit.SPADES,
            point = 1,
            arithmeticValue = 1,
            id = 3,
            icon = R.drawable.ic_ace_of_spades,
        )

        val HEARTS = ArithmeticCard(
            Rank.ACE,
            Suit.HEARTS,
            point = 1,
            arithmeticValue = 1,
            id = 3,
            icon = R.drawable.ic_ace_of_hearts,
        )
    }

    object TWO {
        val DIAMONDS = ArithmeticCard(
            Rank.TWO,
            Suit.DIAMONDS,
            arithmeticValue = 2,
            id = 5,
            icon = R.drawable.ic_two_of_diamonds,
        )

        val CLUBS = ArithmeticCard(
            Rank.TWO,
            Suit.CLUBS,
            point = 2,
            arithmeticValue = 2,
            id = 6,
            icon = R.drawable.ic_two_of_clubs,
        )

        val SPADES = ArithmeticCard(
            Rank.TWO,
            Suit.SPADES,
            arithmeticValue = 2,
            id = 7,
            icon = R.drawable.ic_two_of_spades,
        )

        val HEARTS = ArithmeticCard(
            Rank.TWO,
            Suit.HEARTS,
            arithmeticValue = 2,
            id = 8,
            icon = R.drawable.ic_two_of_hearts,
        )
    }

    object THREE {
        val DIAMONDS = ArithmeticCard(
            Rank.THREE,
            Suit.DIAMONDS,
            arithmeticValue = 3,
            id = 9,
            icon = R.drawable.ic_three_of_diamonds,
        )

        val CLUBS = ArithmeticCard(
            Rank.THREE,
            Suit.CLUBS,
            arithmeticValue = 3,
            id = 10,
            icon = R.drawable.ic_three_of_clubs,
        )

        val SPADES = ArithmeticCard(
            Rank.THREE,
            Suit.SPADES,
            arithmeticValue = 3,
            id = 11,
            icon = R.drawable.ic_three_of_spades,
        )

        val HEARTS = ArithmeticCard(
            Rank.THREE,
            Suit.HEARTS,
            arithmeticValue = 3,
            id = 12,
            icon = R.drawable.ic_three_of_hearts,
        )
    }

    object FOUR {
        val DIAMONDS = ArithmeticCard(
            Rank.FOUR,
            Suit.DIAMONDS,
            arithmeticValue = 4,
            id = 13,
            icon = R.drawable.ic_four_of_diamonds,
        )

        val CLUBS = ArithmeticCard(
            Rank.FOUR,
            Suit.CLUBS,
            arithmeticValue = 4,
            id = 14,
            icon = R.drawable.ic_four_of_clubs,
        )

        val SPADES = ArithmeticCard(
            Rank.FOUR,
            Suit.SPADES,
            arithmeticValue = 4,
            id = 15,
            icon = R.drawable.ic_four_of_spades,
        )

        val HEARTS = ArithmeticCard(
            Rank.FOUR,
            Suit.HEARTS,
            arithmeticValue = 4,
            id = 16,
            icon = R.drawable.ic_four_of_hearts,
        )
    }

    object FIVE {
        val DIAMONDS = ArithmeticCard(
            Rank.FIVE,
            Suit.DIAMONDS,
            arithmeticValue = 5,
            id = 17,
            icon = R.drawable.ic_five_of_diamonds,
        )

        val CLUBS = ArithmeticCard(
            Rank.FIVE,
            Suit.CLUBS,
            arithmeticValue = 5,
            id = 18,
            icon = R.drawable.ic_five_of_clubs,
        )

        val SPADES = ArithmeticCard(
            Rank.FIVE,
            Suit.SPADES,
            arithmeticValue = 5,
            id = 19,
            icon = R.drawable.ic_five_of_spades,
        )

        val HEARTS = ArithmeticCard(
            Rank.FIVE,
            Suit.HEARTS,
            arithmeticValue = 5,
            id = 20,
            icon = R.drawable.ic_five_of_hearts,
        )
    }

    object SIX {
        val DIAMONDS = ArithmeticCard(
            Rank.SIX,
            Suit.DIAMONDS,
            arithmeticValue = 6,
            id = 21,
            icon = R.drawable.ic_six_of_diamonds,
        )

        val CLUBS = ArithmeticCard(
            Rank.SIX,
            Suit.CLUBS,
            arithmeticValue = 6,
            id = 22,
            icon = R.drawable.ic_six_of_clubs,
        )

        val SPADES = ArithmeticCard(
            Rank.SIX,
            Suit.SPADES,
            arithmeticValue = 6,
            id = 23,
            icon = R.drawable.ic_six_of_spades,
        )

        val HEARTS = ArithmeticCard(
            Rank.SIX,
            Suit.HEARTS,
            arithmeticValue = 6,
            id = 24,
            icon = R.drawable.ic_six_of_hearts,
        )
    }

    object SEVEN {
        val DIAMONDS = ArithmeticCard(
            Rank.SEVEN,
            Suit.DIAMONDS,
            arithmeticValue = 7,
            id = 25,
            icon = R.drawable.ic_seven_of_diamonds,
        )

        val CLUBS = ArithmeticCard(
            Rank.SEVEN,
            Suit.CLUBS,
            arithmeticValue = 7,
            id = 26,
            icon = R.drawable.ic_seven_of_clubs,
        )

        val SPADES = ArithmeticCard(
            Rank.SEVEN,
            Suit.SPADES,
            arithmeticValue = 7,
            id = 27,
            icon = R.drawable.ic_seven_of_spades,
        )

        val HEARTS = ArithmeticCard(
            Rank.SEVEN,
            Suit.HEARTS,
            arithmeticValue = 7,
            id = 28,
            icon = R.drawable.ic_seven_of_hearts,
        )
    }

    object EIGHT {
        val DIAMONDS = ArithmeticCard(
            Rank.EIGHT,
            Suit.DIAMONDS,
            arithmeticValue = 8,
            id = 29,
            icon = R.drawable.ic_eight_of_diamonds,
        )

        val CLUBS = ArithmeticCard(
            Rank.EIGHT,
            Suit.CLUBS,
            arithmeticValue = 8,
            id = 30,
            icon = R.drawable.ic_eight_of_clubs,
        )

        val SPADES = ArithmeticCard(
            Rank.EIGHT,
            Suit.SPADES,
            arithmeticValue = 8,
            id = 31,
            icon = R.drawable.ic_eight_of_spades,
        )

        val HEARTS = ArithmeticCard(
            Rank.EIGHT,
            Suit.HEARTS,
            arithmeticValue = 8,
            id = 32,
            icon = R.drawable.ic_eight_of_hearts,
        )
    }

    object NINE {
        val DIAMONDS = ArithmeticCard(
            Rank.NINE,
            Suit.DIAMONDS,
            arithmeticValue = 9,
            id = 33,
            icon = R.drawable.ic_nine_of_diamonds,
        )

        val CLUBS = ArithmeticCard(
            Rank.NINE,
            Suit.CLUBS,
            arithmeticValue = 9,
            id = 34,
            icon = R.drawable.ic_nine_of_clubs,
        )

        val SPADES = ArithmeticCard(
            Rank.NINE,
            Suit.SPADES,
            arithmeticValue = 9,
            id = 35,
            icon = R.drawable.ic_nine_of_spades,
        )

        val HEARTS = ArithmeticCard(
            Rank.NINE,
            Suit.HEARTS,
            arithmeticValue = 9,
            id = 36,
            icon = R.drawable.ic_nine_of_hearts,
        )
    }

    object TEN {
        val DIAMONDS = ArithmeticCard(
            Rank.TEN,
            Suit.DIAMONDS,
            point = 3,
            arithmeticValue = 10,
            id = 37,
            icon = R.drawable.ic_ten_of_diamonds,
        )

        val CLUBS = ArithmeticCard(
            Rank.TEN,
            Suit.CLUBS,
            arithmeticValue = 10,
            id = 38,
            icon = R.drawable.ic_ten_of_clubs,
        )

        val SPADES = ArithmeticCard(
            Rank.TEN,
            Suit.SPADES,
            arithmeticValue = 10,
            id = 39,
            icon = R.drawable.ic_ten_of_spades,
        )

        val HEARTS = ArithmeticCard(
            Rank.TEN,
            Suit.HEARTS,
            arithmeticValue = 10,
            id = 40,
            icon = R.drawable.ic_ten_of_hearts,
        )
    }

    object JACK {
        val DIAMONDS =
            JackCard(
                suit = Suit.DIAMONDS, id = 41, icon = R.drawable.ic_jack_of_diamonds,
            )
        val CLUBS = JackCard(
            suit = Suit.CLUBS, id = 42, icon = R.drawable.ic_jack_of_clubs,
        )
        val SPADES = JackCard(
            suit = Suit.SPADES, id = 43, icon = R.drawable.ic_jack_of_spades,
        )
        val HEARTS = JackCard(
            suit = Suit.HEARTS, id = 44, icon = R.drawable.ic_jack_of_hearts,
        )
    }

    object QUEEN {
        val DIAMONDS =
            QueenCard(
                suit = Suit.DIAMONDS, id = 45, icon = R.drawable.ic_queen_of_diamonds,
            )
        val CLUBS = QueenCard(
            suit = Suit.CLUBS, id = 46, icon = R.drawable.ic_queen_of_clubs,
        )
        val SPADES = QueenCard(
            suit = Suit.SPADES, id = 47, icon = R.drawable.ic_queen_of_spades,
        )
        val HEARTS = QueenCard(
            suit = Suit.HEARTS, id = 48, icon = R.drawable.ic_queen_of_hearts,
        )
    }

    object KING {
        val DIAMONDS =
            KingCard(
                suit = Suit.DIAMONDS, id = 49, icon = R.drawable.ic_king_of_diamonds,
            )
        val CLUBS = KingCard(
            suit = Suit.CLUBS, id = 50, icon = R.drawable.ic_king_of_clubs,
        )
        val SPADES = KingCard(
            suit = Suit.SPADES, id = 51, icon = R.drawable.ic_king_of_spades,
            )
        val HEARTS = KingCard(
            suit = Suit.HEARTS, id = 52, icon = R.drawable.ic_king_of_hearts,
            )
    }
}

val DECK: MutableList<Card> = mutableListOf(
    CARDS.ACE.DIAMONDS,
    CARDS.ACE.CLUBS,
    CARDS.ACE.SPADES,
    CARDS.ACE.HEARTS,

    CARDS.TWO.DIAMONDS,
    CARDS.TWO.CLUBS,
    CARDS.TWO.SPADES,
    CARDS.TWO.HEARTS,

    CARDS.THREE.DIAMONDS,
    CARDS.THREE.CLUBS,
    CARDS.THREE.SPADES,
    CARDS.THREE.HEARTS,

    CARDS.FOUR.DIAMONDS,
    CARDS.FOUR.CLUBS,
    CARDS.FOUR.SPADES,
    CARDS.FOUR.HEARTS,

    CARDS.FIVE.DIAMONDS,
    CARDS.FIVE.CLUBS,
    CARDS.FIVE.SPADES,
    CARDS.FIVE.HEARTS,

    CARDS.SIX.DIAMONDS,
    CARDS.SIX.CLUBS,
    CARDS.SIX.SPADES,
    CARDS.SIX.HEARTS,

    CARDS.SEVEN.DIAMONDS,
    CARDS.SEVEN.CLUBS,
    CARDS.SEVEN.SPADES,
    CARDS.SEVEN.HEARTS,

    CARDS.EIGHT.DIAMONDS,
    CARDS.EIGHT.CLUBS,
    CARDS.EIGHT.SPADES,
    CARDS.EIGHT.HEARTS,

    CARDS.NINE.DIAMONDS,
    CARDS.NINE.CLUBS,
    CARDS.NINE.SPADES,
    CARDS.NINE.HEARTS,

    CARDS.TEN.DIAMONDS,
    CARDS.TEN.CLUBS,
    CARDS.TEN.SPADES,
    CARDS.TEN.HEARTS,

    CARDS.JACK.DIAMONDS,
    CARDS.JACK.CLUBS,
    CARDS.JACK.SPADES,
    CARDS.JACK.HEARTS,

    CARDS.QUEEN.DIAMONDS,
    CARDS.QUEEN.CLUBS,
    CARDS.QUEEN.SPADES,
    CARDS.QUEEN.HEARTS,

    CARDS.KING.DIAMONDS,
    CARDS.KING.CLUBS,
    CARDS.KING.SPADES,
    CARDS.KING.HEARTS
).shuffled().toMutableList()

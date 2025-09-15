package com.example.pastyra.domain.model

import com.example.pastyra.R

enum class Suit {
    HEARTS,
    DIAMONDS,
    CLUBS,
    SPADES,
}

enum class Rank {
    ACE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING,
}

object CARDS {
    object ACE {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.ACE,
                Suit.DIAMONDS,
                point = 1,
                arithmeticValue = 1,
                icon = R.drawable.ic_ace_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.ACE,
                Suit.CLUBS,
                point = 1,
                arithmeticValue = 1,
                icon = R.drawable.ic_ace_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.ACE,
                Suit.SPADES,
                point = 1,
                arithmeticValue = 1,
                icon = R.drawable.ic_ace_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.ACE,
                Suit.HEARTS,
                point = 1,
                arithmeticValue = 1,
                icon = R.drawable.ic_ace_of_hearts,
            )
    }

    object TWO {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.TWO,
                Suit.DIAMONDS,
                arithmeticValue = 2,
                icon = R.drawable.ic_two_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.TWO,
                Suit.CLUBS,
                point = 2,
                arithmeticValue = 2,
                icon = R.drawable.ic_two_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.TWO,
                Suit.SPADES,
                arithmeticValue = 2,
                icon = R.drawable.ic_two_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.TWO,
                Suit.HEARTS,
                arithmeticValue = 2,
                icon = R.drawable.ic_two_of_hearts,
            )
    }

    object THREE {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.THREE,
                Suit.DIAMONDS,
                arithmeticValue = 3,
                icon = R.drawable.ic_three_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.THREE,
                Suit.CLUBS,
                arithmeticValue = 3,
                icon = R.drawable.ic_three_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.THREE,
                Suit.SPADES,
                arithmeticValue = 3,
                icon = R.drawable.ic_three_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.THREE,
                Suit.HEARTS,
                arithmeticValue = 3,
                icon = R.drawable.ic_three_of_hearts,
            )
    }

    object FOUR {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.FOUR,
                Suit.DIAMONDS,
                arithmeticValue = 4,
                icon = R.drawable.ic_four_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.FOUR,
                Suit.CLUBS,
                arithmeticValue = 4,
                icon = R.drawable.ic_four_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.FOUR,
                Suit.SPADES,
                arithmeticValue = 4,
                icon = R.drawable.ic_four_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.FOUR,
                Suit.HEARTS,
                arithmeticValue = 4,
                icon = R.drawable.ic_four_of_hearts,
            )
    }

    object FIVE {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.FIVE,
                Suit.DIAMONDS,
                arithmeticValue = 5,
                icon = R.drawable.ic_five_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.FIVE,
                Suit.CLUBS,
                arithmeticValue = 5,
                icon = R.drawable.ic_five_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.FIVE,
                Suit.SPADES,
                arithmeticValue = 5,
                icon = R.drawable.ic_five_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.FIVE,
                Suit.HEARTS,
                arithmeticValue = 5,
                icon = R.drawable.ic_five_of_hearts,
            )
    }

    object SIX {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.SIX,
                Suit.DIAMONDS,
                arithmeticValue = 6,
                icon = R.drawable.ic_six_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.SIX,
                Suit.CLUBS,
                arithmeticValue = 6,
                icon = R.drawable.ic_six_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.SIX,
                Suit.SPADES,
                arithmeticValue = 6,
                icon = R.drawable.ic_six_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.SIX,
                Suit.HEARTS,
                arithmeticValue = 6,
                icon = R.drawable.ic_six_of_hearts,
            )
    }

    object SEVEN {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.SEVEN,
                Suit.DIAMONDS,
                arithmeticValue = 7,
                icon = R.drawable.ic_seven_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.SEVEN,
                Suit.CLUBS,
                arithmeticValue = 7,
                icon = R.drawable.ic_seven_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.SEVEN,
                Suit.SPADES,
                arithmeticValue = 7,
                icon = R.drawable.ic_seven_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.SEVEN,
                Suit.HEARTS,
                arithmeticValue = 7,
                icon = R.drawable.ic_seven_of_hearts,
            )
    }

    object EIGHT {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.EIGHT,
                Suit.DIAMONDS,
                arithmeticValue = 8,
                icon = R.drawable.ic_eight_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.EIGHT,
                Suit.CLUBS,
                arithmeticValue = 8,
                icon = R.drawable.ic_eight_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.EIGHT,
                Suit.SPADES,
                arithmeticValue = 8,
                icon = R.drawable.ic_eight_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.EIGHT,
                Suit.HEARTS,
                arithmeticValue = 8,
                icon = R.drawable.ic_eight_of_hearts,
            )
    }

    object NINE {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.NINE,
                Suit.DIAMONDS,
                arithmeticValue = 9,
                icon = R.drawable.ic_nine_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.NINE,
                Suit.CLUBS,
                arithmeticValue = 9,
                icon = R.drawable.ic_nine_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.NINE,
                Suit.SPADES,
                arithmeticValue = 9,
                icon = R.drawable.ic_nine_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.NINE,
                Suit.HEARTS,
                arithmeticValue = 9,
                icon = R.drawable.ic_nine_of_hearts,
            )
    }

    object TEN {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.TEN,
                Suit.DIAMONDS,
                point = 3,
                arithmeticValue = 10,
                icon = R.drawable.ic_ten_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.TEN,
                Suit.CLUBS,
                arithmeticValue = 10,
                icon = R.drawable.ic_ten_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.TEN,
                Suit.SPADES,
                arithmeticValue = 10,
                icon = R.drawable.ic_ten_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                Rank.TEN,
                Suit.HEARTS,
                arithmeticValue = 10,
                icon = R.drawable.ic_ten_of_hearts,
            )
    }

    object JACK {
        val DIAMONDS =
            Card.JackCard(
                suit = Suit.DIAMONDS,
                icon = R.drawable.ic_jack_of_diamonds,
            )
        val CLUBS =
            Card.JackCard(
                suit = Suit.CLUBS,
                icon = R.drawable.ic_jack_of_clubs,
            )
        val SPADES =
            Card.JackCard(
                suit = Suit.SPADES,
                icon = R.drawable.ic_jack_of_spades,
            )
        val HEARTS =
            Card.JackCard(
                suit = Suit.HEARTS,
                icon = R.drawable.ic_jack_of_hearts,
            )
    }

    object QUEEN {
        val DIAMONDS =
            Card.QueenCard(
                suit = Suit.DIAMONDS,
                icon = R.drawable.ic_queen_of_diamonds,
            )
        val CLUBS =
            Card.QueenCard(
                suit = Suit.CLUBS,
                icon = R.drawable.ic_queen_of_clubs,
            )
        val SPADES =
            Card.QueenCard(
                suit = Suit.SPADES,
                icon = R.drawable.ic_queen_of_spades,
            )
        val HEARTS =
            Card.QueenCard(
                suit = Suit.HEARTS,
                icon = R.drawable.ic_queen_of_hearts,
            )
    }

    object KING {
        val DIAMONDS =
            Card.KingCard(
                suit = Suit.DIAMONDS,
                icon = R.drawable.ic_king_of_diamonds,
            )
        val CLUBS =
            Card.KingCard(
                suit = Suit.CLUBS,
                icon = R.drawable.ic_king_of_clubs,
            )
        val SPADES =
            Card.KingCard(
                suit = Suit.SPADES,
                icon = R.drawable.ic_king_of_spades,
            )
        val HEARTS =
            Card.KingCard(
                suit = Suit.HEARTS,
                icon = R.drawable.ic_king_of_hearts,
            )
    }
}

val DECK: MutableList<Card> =
    mutableListOf(
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
        CARDS.KING.HEARTS,
    ).shuffled().toMutableList()

val DECK1: MutableList<Card> =
    mutableListOf(
        CARDS.ACE.DIAMONDS,
        CARDS.THREE.DIAMONDS,
        CARDS.SEVEN.CLUBS,
        CARDS.TWO.HEARTS,
        CARDS.TWO.HEARTS,
        CARDS.FIVE.DIAMONDS,
        CARDS.QUEEN.CLUBS,
        CARDS.QUEEN.SPADES,
        CARDS.QUEEN.HEARTS,
        CARDS.JACK.DIAMONDS,
        CARDS.JACK.SPADES,
        CARDS.TWO.CLUBS,
    ).toMutableList()

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
                rank = Rank.ACE,
                suit = Suit.DIAMONDS,
                arithmeticValue = 1,
                icon = R.drawable.ic_ace_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.ACE,
                suit = Suit.CLUBS,
                arithmeticValue = 1,
                icon = R.drawable.ic_ace_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.ACE,
                suit = Suit.SPADES,
                arithmeticValue = 1,
                icon = R.drawable.ic_ace_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.ACE,
                suit = Suit.HEARTS,
                arithmeticValue = 1,
                icon = R.drawable.ic_ace_of_hearts,
            )
    }

    object TWO {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.TWO,
                suit = Suit.DIAMONDS,
                arithmeticValue = 2,
                icon = R.drawable.ic_two_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.TWO,
                suit = Suit.CLUBS,
                arithmeticValue = 2,
                icon = R.drawable.ic_two_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.TWO,
                suit = Suit.SPADES,
                arithmeticValue = 2,
                icon = R.drawable.ic_two_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.TWO,
                suit = Suit.HEARTS,
                arithmeticValue = 2,
                icon = R.drawable.ic_two_of_hearts,
            )
    }

    object THREE {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.THREE,
                suit = Suit.DIAMONDS,
                arithmeticValue = 3,
                icon = R.drawable.ic_three_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.THREE,
                suit = Suit.CLUBS,
                arithmeticValue = 3,
                icon = R.drawable.ic_three_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.THREE,
                suit = Suit.SPADES,
                arithmeticValue = 3,
                icon = R.drawable.ic_three_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.THREE,
                suit = Suit.HEARTS,
                arithmeticValue = 3,
                icon = R.drawable.ic_three_of_hearts,
            )
    }

    object FOUR {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.FOUR,
                suit = Suit.DIAMONDS,
                arithmeticValue = 4,
                icon = R.drawable.ic_four_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.FOUR,
                suit = Suit.CLUBS,
                arithmeticValue = 4,
                icon = R.drawable.ic_four_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.FOUR,
                suit = Suit.SPADES,
                arithmeticValue = 4,
                icon = R.drawable.ic_four_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.FOUR,
                suit = Suit.HEARTS,
                arithmeticValue = 4,
                icon = R.drawable.ic_four_of_hearts,
            )
    }

    object FIVE {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.FIVE,
                suit = Suit.DIAMONDS,
                arithmeticValue = 5,
                icon = R.drawable.ic_five_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.FIVE,
                suit = Suit.CLUBS,
                arithmeticValue = 5,
                icon = R.drawable.ic_five_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.FIVE,
                suit = Suit.SPADES,
                arithmeticValue = 5,
                icon = R.drawable.ic_five_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.FIVE,
                suit = Suit.HEARTS,
                arithmeticValue = 5,
                icon = R.drawable.ic_five_of_hearts,
            )
    }

    object SIX {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.SIX,
                suit = Suit.DIAMONDS,
                arithmeticValue = 6,
                icon = R.drawable.ic_six_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.SIX,
                suit = Suit.CLUBS,
                arithmeticValue = 6,
                icon = R.drawable.ic_six_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.SIX,
                suit = Suit.SPADES,
                arithmeticValue = 6,
                icon = R.drawable.ic_six_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.SIX,
                suit = Suit.HEARTS,
                arithmeticValue = 6,
                icon = R.drawable.ic_six_of_hearts,
            )
    }

    object SEVEN {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.SEVEN,
                suit = Suit.DIAMONDS,
                arithmeticValue = 7,
                icon = R.drawable.ic_seven_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.SEVEN,
                suit = Suit.CLUBS,
                arithmeticValue = 7,
                icon = R.drawable.ic_seven_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.SEVEN,
                suit = Suit.SPADES,
                arithmeticValue = 7,
                icon = R.drawable.ic_seven_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.SEVEN,
                suit = Suit.HEARTS,
                arithmeticValue = 7,
                icon = R.drawable.ic_seven_of_hearts,
            )
    }

    object EIGHT {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.EIGHT,
                suit = Suit.DIAMONDS,
                arithmeticValue = 8,
                icon = R.drawable.ic_eight_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.EIGHT,
                suit = Suit.CLUBS,
                arithmeticValue = 8,
                icon = R.drawable.ic_eight_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.EIGHT,
                suit = Suit.SPADES,
                arithmeticValue = 8,
                icon = R.drawable.ic_eight_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.EIGHT,
                suit = Suit.HEARTS,
                arithmeticValue = 8,
                icon = R.drawable.ic_eight_of_hearts,
            )
    }

    object NINE {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.NINE,
                suit = Suit.DIAMONDS,
                arithmeticValue = 9,
                icon = R.drawable.ic_nine_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.NINE,
                suit = Suit.CLUBS,
                arithmeticValue = 9,
                icon = R.drawable.ic_nine_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.NINE,
                suit = Suit.SPADES,
                arithmeticValue = 9,
                icon = R.drawable.ic_nine_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.NINE,
                suit = Suit.HEARTS,
                arithmeticValue = 9,
                icon = R.drawable.ic_nine_of_hearts,
            )
    }

    object TEN {
        val DIAMONDS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.TEN,
                suit = Suit.DIAMONDS,
                arithmeticValue = 10,
                icon = R.drawable.ic_ten_of_diamonds,
            )

        val CLUBS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.TEN,
                suit = Suit.CLUBS,
                arithmeticValue = 10,
                icon = R.drawable.ic_ten_of_clubs,
            )

        val SPADES =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.TEN,
                suit = Suit.SPADES,
                arithmeticValue = 10,
                icon = R.drawable.ic_ten_of_spades,
            )

        val HEARTS =
            Card.AnyArithmeticCard.ArithmeticCard(
                rank = Rank.TEN,
                suit = Suit.HEARTS,
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

val DECK1: List<Card> =
    listOf(
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
    ).shuffled()

// --------------------------------------------------------------------------------------------------

val DECK: List<Card> = // TESTED DECK
    listOf(
        CARDS.ACE.DIAMONDS,
        CARDS.SEVEN.CLUBS, // 1 player
        CARDS.ACE.SPADES,
        CARDS.TEN.DIAMONDS,
        //
        CARDS.EIGHT.DIAMONDS,
        CARDS.EIGHT.CLUBS,
        CARDS.ACE.HEARTS, // 2 player
        CARDS.NINE.HEARTS,
        //
        CARDS.SIX.DIAMONDS,
        CARDS.ACE.CLUBS,
        CARDS.EIGHT.SPADES, // table
        CARDS.TEN.HEARTS,
    )

package com.example.pastyra.domain.fixtures

import com.example.pastyra.domain.model.CARDS
import com.example.pastyra.domain.model.Card

// WARNING: Для четкого тестирования в идеале сразу отсортированные карты добавлять.
// Чтобы в тестах заранее знали очередность
val DECK_PLAYER_CAN_MAKE_PASTYRE: List<Card> =
    listOf(
        CARDS.SIX.DIAMONDS,
        CARDS.EIGHT.DIAMONDS,
        CARDS.NINE.DIAMONDS,
        CARDS.TEN.DIAMONDS,
        //
        CARDS.TWO.DIAMONDS,
        CARDS.THREE.DIAMONDS,
        CARDS.FOUR.DIAMONDS,
        CARDS.FIVE.DIAMONDS,
        //
        CARDS.ACE.SPADES,
        CARDS.ACE.DIAMONDS,
        CARDS.EIGHT.SPADES,
        CARDS.TEN.SPADES,
    )

// val DECK_PLAYER_CAN_NOT_MAKE_PASTYRE: List<Card> =
//    listOf(
//        CARDS.TEN.DIAMONDS,
//        //
//        CARDS.ACE.SPADES,
//        CARDS.ACE.DIAMONDS,
//        CARDS.EIGHT.SPADES,
//        CARDS.TEN.SPADES,
//    )

val DECK_BUNDLE_TEST: List<Card> = // TESTED DECK
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
        CARDS.TWO.SPADES, // table
        CARDS.TEN.HEARTS,
    )

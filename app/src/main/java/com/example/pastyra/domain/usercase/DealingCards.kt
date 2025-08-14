package com.example.pastyra.domain.usercase

import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.Player

fun dealPlayingCard(deck: MutableList<Card>): MutableList<Card> {
    val playerHandCard: MutableList<Card> = mutableListOf()

    repeat(4) {
        playerHandCard.add(deck.removeAt(0))
    }

    return playerHandCard
}

fun dealInitialCard(
    deck: MutableList<Card>,
    playerList: List<Player>,
    deckOnTable: MutableList<Card>
) {
    playerList.forEach { player ->
        player.cardsHand.run { dealPlayingCard(deck) }
    }

    deckOnTable.run { dealPlayingCard(deck) }
}

package com.example.pastyra.domain.model

data class Player(
    val playerId: Int = 1,
    val activeCard: Card? = null,
    val cardToBeBundled: ArithmeticCard? = null,
    val bundlingCard: BundleCard? = null,
    val cardsHand: List<Card> = emptyList(),
    val cardsTaken: List<Card> = emptyList(),
    val isActive: Boolean = false,
)

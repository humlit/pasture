package com.example.pastyra.domain.model

import java.util.UUID

data class Player(
    val playerId: UUID = UUID.randomUUID(),
    val activeCard: Card? = null,
    val cardToBeBundled: Card.AnyArithmeticCard.ArithmeticCard? = null,
    val bundlingCard: Card.AnyArithmeticCard.BundleCard? = null,
    val cardsHand: List<Card> = emptyList(),
    val cardsTaken: List<Card> = emptyList(),
    val pastyrs: Int = 0,
    val isActive: Boolean = false,
)

package com.example.pastyra.domain.model

data class Card(
    val rank: Rank,
    val suit: Suit,
    val point: Int = 0,
    val arithmeticValue: Int? = null,
    val canJackPickUp: Boolean = true,
    val id: Int = 0,
) {

}

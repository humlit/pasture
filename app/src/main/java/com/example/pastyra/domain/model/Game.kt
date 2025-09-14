package com.example.pastyra.domain.model

import java.util.UUID

data class Game(
    val playerList: List<Player> = emptyList(),
    val playerOrderIds: List<UUID> = emptyList(),
    val lastTakenCardPlayer: Player? = null,
    val gameDeck: List<Card> = DECK,
    val tableDeck: List<Card> = emptyList(),
    val tableActiveCards: List<Card> = emptyList(),
    val temporaryPlayerTakenCards: List<Card> = emptyList(),
    val temporaryPlayerBundlingCards: List<List<Card.AnyArithmeticCard>> = emptyList(),
    var isStarted: Boolean = false,
    val gameStage: Stage = Stage.DefaultStage,
)

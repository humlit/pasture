package com.example.pastyra.domain.model

data class Game(
    var activePlayer: Player? = null,
    val playerList: List<Player> = emptyList(),
    val gameDeck: List<Card> = DECK,
    val tableDeck: List<Card> = emptyList(),
    val tableActiveCard: List<Card> = emptyList(),
    var isStarted: Boolean = false,
    val gameStage: Stage = Stage.Waiting,
) {

}

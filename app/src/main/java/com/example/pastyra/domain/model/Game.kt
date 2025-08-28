package com.example.pastyra.domain.model

data class Game(
    val playerList: List<Player> = emptyList(),
    val gameDeck: List<Card> = DECK,
    val tableDeck: List<Card> = emptyList(),
    val tableActiveCard: List<Card> = emptyList(),
    val temporaryPlayerTakenCard: List<Card> = emptyList(),
    var isStarted: Boolean = false,
    val gameStage: Stage = Stage.DefaultStage,
) {

}

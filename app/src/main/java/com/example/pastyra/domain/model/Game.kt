package com.example.pastyra.domain.model

data class Game(
    val playerList: List<Player> = emptyList(),
    val gameDeck: List<Card> = DECK,
    val tableDeck: List<Card> = emptyList(),
    val tableActiveCards: List<Card> = emptyList(),
    val temporaryPlayerTakenCards: List<Card> = emptyList(),
    val temporaryPlayerBundlingCards: List<List<Card>> = emptyList(),
    var isStarted: Boolean = false,
    val gameStage: Stage = Stage.DefaultStage,
) {

}

package com.example.pastyra.domain.usercase

import android.util.Log
import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.CardWithRank
import com.example.pastyra.domain.model.GameState
import com.example.pastyra.domain.model.safeRank

fun selectArithmeticTableCard(card: Card, state: GameState, playerId: Int): MutableList<Card> {
    val newTableActiveCard = state.game.tableActiveCard.toMutableList()
    val currentPlayer = state.game.playerList.find { it.playerId == playerId }

    if (newTableActiveCard.any { it.id == card.id }) {
        newTableActiveCard.removeAll { it.id == card.id }
    } else if (card is ArithmeticCard) {
        newTableActiveCard.add(card)
    }

    //TODO ВЕРНУТЬ СЮДА ПРОВЕРКУ -> НА ГИТХАБЕ ОНА ЕСТЬ (ИЗ ЗА НЕЁ UI ОБНОВЛЯЕТСЯ КОРРЕКТНО, НО ТРЕБУЕТСЯ ДОП НАЖАТИЕ В КОНЦЕ КУДА УГОДНО) ИСРПАВИТЬ

    return newTableActiveCard
}

fun selectQueenAndKingCard(card: Card, state: GameState, playerId: Int): MutableList<Card> {
    val newTableActiveCard = state.game.tableActiveCard.toMutableList()

    if (newTableActiveCard.isNotEmpty() && card is CardWithRank && card.rank == state.game.activePlayer?.activeCard?.safeRank) {
        newTableActiveCard.clear()
    }

    val currentPlayer = state.game.playerList.find { it.playerId == playerId }

    if (card is CardWithRank && currentPlayer?.activeCard is CardWithRank && card.rank == currentPlayer.activeCard.rank) {
        newTableActiveCard.add(card)
    }

    return newTableActiveCard
}
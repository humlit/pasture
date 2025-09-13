package com.example.pastyra.domain.usercase

import com.example.pastyra.domain.model.Card

fun isPlayerCanPutCardOnTable(
    deck: List<Card>, tableActiveCards: List<Card>, cardToPutOnTable: Card
): Boolean {
//    TODO: добавить проверку на то, что у пользователя есть завязка -> тогда нельзя ставить карты на стол
    return !isPlayerCanTakeCard(deck, tableActiveCards, cardToPutOnTable)
}

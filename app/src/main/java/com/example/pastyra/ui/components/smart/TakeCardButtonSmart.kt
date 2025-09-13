package com.example.pastyra.ui.components.smart

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.domain.model.KingCard
import com.example.pastyra.domain.model.QueenCard
import com.example.pastyra.domain.usercase.isPlayerCanTakeCard
import com.example.pastyra.ui.components.TakeCardButton
import com.example.pastyra.ui.viewmodel.GameViewModel

@Composable
fun TakeCardButtonSmart(viewModel: GameViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val game = state.value.game

    val tableDeck = game.tableDeck
    val tableActiveCards = game.tableActiveCards

    if (isPlayerCanTakeCard(
            tableDeck, tableActiveCards, viewModel.requiredActivePlayerCard
        )
    ) {
        TakeCardButton(clickListener = {
            when (viewModel.requiredActivePlayerCard) {
                is ArithmeticCard -> {
                    viewModel.waitToTakeArithmeticCardStage()
                }

                is QueenCard, is KingCard -> {
                    viewModel.waitToTakeQueenOrKingCardStage() //TODO: Переделать весь when
                }

                else -> {
                    viewModel.takeCard()
                }
            }
        })
    }
}
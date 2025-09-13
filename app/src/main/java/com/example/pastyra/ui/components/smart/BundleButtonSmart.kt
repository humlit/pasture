package com.example.pastyra.ui.components.smart

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.ui.components.BundleButton
import com.example.pastyra.ui.viewmodel.GameViewModel
import getPlayerBundlingCards

@Composable
fun BundleButtonSmart(viewModel: GameViewModel, card: ArithmeticCard) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val game = state.value.game
    val activePlayer = viewModel.requiredActivePlayer
    val tableDeck = game.tableDeck
    val tableActiveCards = game.tableActiveCards

    if (getPlayerBundlingCards(activePlayer, tableDeck, tableActiveCards).isNotEmpty()) {
        BundleButton(clickListener = {
            viewModel.startBundling(card)
            viewModel.waitToBundleCardStage()
        })
    }
}
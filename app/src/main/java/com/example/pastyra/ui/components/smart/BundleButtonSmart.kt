package com.example.pastyra.ui.components.smart

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pastyra.ui.components.BundleButton
import com.example.pastyra.ui.viewmodel.GameViewModel
import getPlayerBundlingCards

@Composable
fun BundleButtonSmart(viewModel: GameViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val activePlayer = viewModel.requiredActivePlayer
    val tableDeck = state.value.game.tableDeck

    if (getPlayerBundlingCards(activePlayer, tableDeck).isNotEmpty()) {
        BundleButton(clickListener = {
            viewModel.waitToBundleCardStage()
        })
    }
}
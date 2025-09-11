package com.example.pastyra.ui.components.smart

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pastyra.domain.usercase.isPlayerCanPutCardOnTable
import com.example.pastyra.ui.components.PutCardOnTableButton
import com.example.pastyra.ui.viewmodel.GameViewModel

@Composable
fun PutCardOnTableButtonSmart(viewModel: GameViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val tableDeck = state.value.game.tableDeck
    val activePlayerCard = viewModel.requiredActivePlayerCard
    val isActivePlayerHasBundleCard = viewModel.requiredActivePlayer.bundlingCard == null

    if (isPlayerCanPutCardOnTable(tableDeck, activePlayerCard) && isActivePlayerHasBundleCard) {
        PutCardOnTableButton(clickListener = {
            viewModel.putCardOnTable()
        })
    }
}
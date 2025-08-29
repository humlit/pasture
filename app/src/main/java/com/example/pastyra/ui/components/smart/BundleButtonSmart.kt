package com.example.pastyra.ui.components.smart

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.ui.components.BundleButton
import com.example.pastyra.ui.viewmodel.GameViewModel
import isPlayerCanBundleCard

@Composable
fun BundleButtonSmart(viewModel: GameViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val game = state.value.game

    var canPlayerBundle = false
    val activePlayerCard = viewModel.requiredActivePlayerCard

    viewModel.activePlayer?.cardsHand?.forEach { card ->
        if (card is ArithmeticCard && activePlayerCard is ArithmeticCard && isPlayerCanBundleCard(
                game.tableDeck, activePlayerCard, card
            )
        ) {
            canPlayerBundle = true
        }
    }

    if (canPlayerBundle) {
        BundleButton(clickListener = {

        })
    }
}
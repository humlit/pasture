package com.example.pastyra.ui.components.smart

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import canPlayerBundle
import com.example.pastyra.domain.model.ArithmeticCard
import com.example.pastyra.ui.components.BundleButton
import com.example.pastyra.ui.viewmodel.GameViewModel
import isPlayerCanBundleCard

@Composable
fun BundleButtonSmart(viewModel: GameViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val game = state.value.game

    if (canPlayerBundle(viewModel).isNotEmpty()) {
        BundleButton(clickListener = {
            viewModel.waitToBundleCardStage()
        })
    }
}
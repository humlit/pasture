package com.example.pastyra.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.pastyra.R
import com.example.pastyra.domain.model.Player
import com.example.pastyra.ui.viewmodel.GameViewModel

@Composable
fun FinishMoveButton(clickListener: () -> Unit) {
    Button(
        onClick = clickListener
    ) { Text(text = stringResource(R.string.finish_move)) }
}

@Composable
fun BundleButton(clickListener: () -> Unit) {
    Button(
        onClick = clickListener
    ) { Text(text = stringResource(R.string.cards_bundle)) }
}

@Composable
fun PutCardOnTableButton(clickListener: () -> Unit) {
    Button(
        onClick = clickListener
    ) {
        Text(stringResource(R.string.cards_put))
    }
}

@Composable
fun TakeCardButton(clickListener: () -> Unit) {
    Button(
        onClick = clickListener
    ) {
        Text(stringResource(R.string.cards_take))
    }
}

@Composable
fun CancelButton(clickListener: () -> Unit) {
    Button(
        onClick = clickListener
    ) {
        Text(stringResource(R.string.cansel_button))
    }
}

@Composable
fun DefaultButtonPosition(player: Player, viewModel: GameViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BundleButton(clickListener = {
            viewModel.waitToBundleCardStage()
        })

        PutCardOnTableButton(clickListener = {
            viewModel.putCardOnTable()
        })

        TakeCardButtonSmart(viewModel)
    }
}

@Composable
fun PlayerTakeCard(viewModel: GameViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FinishMoveButton(clickListener = {
            viewModel.takeCard()
        })

        CancelButton(clickListener = {

        })
    }
}

@Composable
fun PlayerBundleCard(viewModel: GameViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CancelButton(clickListener = {

        })

        FinishMoveButton(clickListener = {

        })
    }
}

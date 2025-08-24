package com.example.pastyra.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.pastyra.R

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

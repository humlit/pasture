package com.example.pastyra.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.pastyra.R
import com.example.pastyra.domain.model.Card
import com.example.pastyra.ui.components.smart.PutCardOnTableButtonSmart
import com.example.pastyra.ui.components.smart.TakeCardButtonSmart
import com.example.pastyra.ui.viewmodel.GameViewModel

@Composable
fun DisplayCards(
    viewModel: GameViewModel,
    showDialog: Boolean,
    listOfCards: List<Card>,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    clickListener: (Card) -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,

            title = {
                Text(
                    text = "Выберите карты для удаления ",
                    fontSize = 16.sp,
                )
            },

            text = {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(listOfCards) { card ->
                            CardUIView(
                                card = card,
                                onClick = { clickListener(card) },
                                viewModel = viewModel,
                            )
                        }
                }
            },

            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = onDismiss
                    ) { Text(text = stringResource(R.string.cancel_button)) }

                    TextButton(
                        onClick = onConfirm
                    ) { Text(text = stringResource(R.string.confirm_button)) }
                }
            },
        )
    }
}

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
    ) {
        Text(
            text = stringResource(R.string.cards_bundle), fontSize = 10.sp
        )
    }
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
        Text(stringResource(R.string.cancel_button))
    }
}

@Composable
fun DefaultButtonPosition(viewModel: GameViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        PutCardOnTableButtonSmart(viewModel)

        TakeCardButtonSmart(viewModel)
    }
}

@Composable
fun PlayerTakeCard(viewModel: GameViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CancelButton(clickListener = {

        })

        FinishMoveButton(clickListener = {

        })
    }
}

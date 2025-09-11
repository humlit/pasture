package com.example.pastyra.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pastyra.R
import com.example.pastyra.domain.model.Card
import com.example.pastyra.ui.components.smart.PutCardOnTableButtonSmart
import com.example.pastyra.ui.components.smart.TakeCardButtonSmart
import com.example.pastyra.ui.viewmodel.GameViewModel

@Composable
fun DisplayCards(
    viewModel: GameViewModel,
    showDialog: Boolean,
    beingCollectedList: List<Card>,
    readyList: List<Card>,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    clickListener: (Card) -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,

            title = {
                Text(
                    text = "Выбранные карты: ",
                    fontSize = 16.sp,
                )
            },

            text = {
                Column {
                    Text(
                        text = if (beingCollectedList.isNotEmpty()) {
                            "Они только растут"
                        } else {
                            ""
                        }
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(beingCollectedList) { card ->
                            CardUIView(
                                card = card,
                                onClick = { clickListener(card) },
                                viewModel = viewModel,
                                isSelected = viewModel.cardForDeleteFromDisplay.any { it.id == card.id })
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = if (readyList.isNotEmpty()) {
                            "Эти карты уже у вас в кармане"
                        } else {
                            "Карман пуст"
                        }
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(readyList) { card ->
                            CardUIView(
                                card = card,
                                onClick = { },
                                viewModel = viewModel,
                            )
                        }
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
fun FinishMoveButton(clickListener: () -> Unit, enabledCondition: Boolean) {
    Button(
        onClick = clickListener,
        enabled = enabledCondition,
    ) { Text(text = stringResource(R.string.finish_move)) }
}

@Composable
fun BundleButton(clickListener: () -> Unit) {
    Button(
        onClick = clickListener, shape = RectangleShape,
        contentPadding = PaddingValues(all = 0.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.cards_bundle),
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Visible,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.SansSerif
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
    val state = viewModel.state.collectAsStateWithLifecycle()
    val game = state.value.game

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FinishMoveButton(
            clickListener = {
                viewModel.takeCard()
            },
            enabledCondition = game.tableActiveCards.isEmpty() && game.temporaryPlayerTakenCards.isNotEmpty()
        )

        CancelButton(clickListener = {

        })
    }
}

@Composable
fun PlayerBundleCard(viewModel: GameViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val game = state.value.game

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CancelButton(clickListener = {

        })
        FinishMoveButton(
            clickListener = {
                viewModel.bundleCard()
            },
            enabledCondition = game.tableActiveCards.isEmpty() && game.temporaryPlayerBundlingCards.isNotEmpty()
        )
    }
}

package com.example.pastyra.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.Stage
import com.example.pastyra.domain.usercase.common.sortedCard
import com.example.pastyra.ui.viewmodel.GameViewModel
import kotlinx.coroutines.launch

@Composable
fun TableUIView(deck: List<Card>, viewModel: GameViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val game = state.value.game
    val scope = rememberCoroutineScope()

    var showDialog by remember { mutableStateOf(false) }

    val rowsCard = deck.sortedCard().chunked(
        4
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row {
            Button(
                onClick = { showDialog = true }
            ) {
                Text(text = "Выбранные карты")
            }

            DisplayCards(
                viewModel = viewModel,
                showDialog = showDialog,
                listOfCards = game.tableActiveCards, //TODO: объединить списки по фазе (Фаза взятия карта = Активные карты + Временный массив уже взятых карт игрока, со связками также) -> у игрока 1 кнопка, но сразу все доступные массивы
                onConfirm = {},
                onDismiss = { showDialog = false },
                clickListener = { card ->

                }
            )
        }
        Column(
        ) {
            rowsCard.forEach { rowCards ->
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowCards.forEach { card ->
                        CardUIView(
                            card = card,
                            isSelected = state.value.game.tableActiveCards.any { it.id == card.id },
                            onClick = {
                                scope.launch {
                                    if (game.gameStage == Stage.TakeArithmeticCard || game.gameStage == Stage.TakeQueenOrKingCard) {
                                        viewModel.selectTableCard(
                                            card = card,
                                        )
                                    } else if (game.gameStage == Stage.WaitToBundle) {
                                        viewModel.selectTableCardForBundle(
                                            card = card
                                        )
                                    }
                                }
                            }, viewModel = viewModel
                        )
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

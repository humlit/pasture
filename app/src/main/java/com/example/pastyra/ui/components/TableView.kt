package com.example.pastyra.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.Stage
import com.example.pastyra.ui.viewmodel.GameViewModel
import kotlinx.coroutines.launch

@Composable
fun TableUIView(deck: List<Card>, viewModel: GameViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val game = state.value.game
    val scope = rememberCoroutineScope()

    val rowsCard = deck.chunked(
        4
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Gray)
    ) {
        Column {
            rowsCard.forEach { rowCards ->
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    rowCards.forEach { card ->
                        CardUIView(
                            card = card,
                            isSelected = state.value.game.tableActiveCard.any { it.id == card.id },
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

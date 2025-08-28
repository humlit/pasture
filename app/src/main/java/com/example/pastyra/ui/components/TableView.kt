package com.example.pastyra.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pastyra.domain.model.Card
import com.example.pastyra.ui.viewmodel.GameViewModel

@Composable
fun TableUIView(deck: List<Card>, viewModel: GameViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()

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
                                viewModel.selectTableCard(
                                    card = card,
                                    playerId = state.value.game.activePlayer?.playerId ?: 0
                                )
                            })

                    }
                }

                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

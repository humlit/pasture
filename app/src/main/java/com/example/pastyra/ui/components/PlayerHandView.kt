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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pastyra.domain.model.Player
import com.example.pastyra.ui.viewmodel.GameViewModel

@Preview
@Composable
fun PreviewPlayerUIView() {

}

@Composable
fun PlayerUIView(player: Player, viewModel: GameViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (player.activeCard != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    BundleButton(clickListener = {
                        viewModel.bundleCard(playerId = player.playerId)
                    })

                    PutCardOnTableButton(clickListener = {
                        viewModel.putCardOnTable(playerId = player.playerId)
                        viewModel.switchActivePlayer()
                    })

                    TakeCardButton(clickListener = {
                        if (player.activeCard.arithmeticValue != null) {
                            viewModel.waitToTakeArithmeticCardStage()
                        } else {
                            viewModel.takeCard(playerId = player.playerId)
                        }
                    })
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                player.cardsHand.forEachIndexed { index, card ->
                    CardUIView(
                        card = card,
                        isSelected = card == player.activeCard,
                        onClick = {
                            viewModel.selectCard(
                                playerId = state.value.game.activePlayer?.playerId ?: 0,
                                card = card
                            )
                        })
                }
            }
        }
    }
}
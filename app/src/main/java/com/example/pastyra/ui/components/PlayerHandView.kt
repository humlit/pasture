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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pastyra.domain.model.CARDS
import com.example.pastyra.domain.model.Player
import com.example.pastyra.domain.model.Stage
import com.example.pastyra.domain.usercase.common.sortedCard
import com.example.pastyra.ui.viewmodel.GameViewModel
import getPlayerBundlingCards

@Preview
@Composable
fun PreviewPlayerUIView() {
    val player = Player(
        playerId = 1,
        isActive = true,
        cardsHand = listOf(CARDS.TEN.DIAMONDS, CARDS.TWO.HEARTS)
    )
    PlayerUIView(player = player, viewModel = viewModel())
}

@Composable
fun PlayerUIView(player: Player, viewModel: GameViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val game = state.value.game

    val scope = rememberCoroutineScope()

    val tableDeck = game.tableDeck
    val tableActiveCards = game.tableActiveCards

    val cardsForBundleCheckList =
        getPlayerBundlingCards(player, tableDeck, tableActiveCards)

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (player.isActive) {
                when {
                    // TODO: Мб кнопка "завершить ход" должна быть всегда, на случай если игрок передумает?
                    state.value.game.gameStage == Stage.TakeArithmeticCard || state.value.game.gameStage == Stage.TakeQueenOrKingCard -> PlayerTakeCard(
                        viewModel
                    )

                    state.value.game.gameStage == Stage.WaitToBundle -> PlayerBundleCard(
                        viewModel
                    )

                    player.activeCard != null -> DefaultButtonPosition(viewModel)
                }

            } else {
                return@Card
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                player.cardsHand.sortedCard().forEachIndexed { index, card ->
                    CardUIView(
                        card = card,
                        isSelected = card == player.activeCard || card == player.cardToBeBundled,
                        isReadyForBundle = cardsForBundleCheckList.any { it.id == card.id },
                        onClick = {
                            viewModel.selectCard(
                                card = card
                            )
                        },
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}
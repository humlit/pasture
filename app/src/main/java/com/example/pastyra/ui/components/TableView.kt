package com.example.pastyra.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun TableUIView(
    deck: List<Card>,
    viewModel: GameViewModel,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val game = state.value.game
    val cardForDeleteFromDisplayChecker = viewModel.cardForDeleteFromDisplay

    var onTableDisplayListOfCard by remember { mutableStateOf(emptyList<Card>()) }
    var showDisplaySelectedTableCards by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    onTableDisplayListOfCard =
        when (game.gameStage) {
            Stage.DefaultStage -> emptyList()
            Stage.WaitToBundle -> game.temporaryPlayerBundlingCards.flatten() + game.tableActiveCards
            Stage.TakeArithmeticCard, Stage.TakeQueenOrKingCard -> game.temporaryPlayerTakenCards + game.tableActiveCards
        }

    val rowsCard =
        deck.sortedCard().chunked(
            4,
        )

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(300.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        LazyRow(
            modifier =
                Modifier
                    .clickable(onClick = { showDisplaySelectedTableCards = true })
                    .background(color = Color.Transparent),
        ) {
            items(onTableDisplayListOfCard) { card ->
                ShortCardUIView(card, onClick = {
                    showDisplaySelectedTableCards = true
                })
            }
        }

        DisplaySelectedTableCards(
            viewModel = viewModel,
            showDialog = showDisplaySelectedTableCards,
            beingCollectedList = game.tableActiveCards,
            readyList =
                if (game.gameStage == Stage.WaitToBundle) {
                    game.temporaryPlayerBundlingCards.flatten()
                } else {
                    game.temporaryPlayerTakenCards
                },
            onConfirm = {
                if (game.gameStage == Stage.WaitToBundle &&
                    game.temporaryPlayerBundlingCards.isEmpty() &&
                    cardForDeleteFromDisplayChecker.any { it == game.tableActiveCards[0] }
                ) {
                    viewModel.playerSelectHisCardInDisplay()
                } else {
                    viewModel.confirmDeleteCardFromDisplay()
                }

                showDisplaySelectedTableCards = false
            },
            onDismiss = {
                showDisplaySelectedTableCards = false
                viewModel.closeDialogBox()
            },
            clickListener = { card ->
                viewModel.selectCardForDeleteFromDisplay(card)
            },
        )

        Spacer(modifier = Modifier.height(5.dp))

        Column {
            rowsCard.forEach { rowCards ->
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    rowCards.forEach { card ->
                        CardUIView(
                            card = card,
                            onClick = {
                                scope.launch {
                                    if (game.gameStage == Stage.TakeArithmeticCard || game.gameStage == Stage.TakeQueenOrKingCard) {
                                        viewModel.selectTableCard(
                                            card = card,
                                        )
                                    } else if (game.gameStage == Stage.WaitToBundle) {
                                        viewModel.selectTableCardForBundle(
                                            card = card,
                                        )
                                    }
                                }
                            },
                            viewModel = viewModel,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

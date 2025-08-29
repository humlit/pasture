package com.example.pastyra.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pastyra.ui.viewmodel.GameViewModel

@Composable
fun PastyraGameStart(
    viewModel: GameViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val game = state.game


    Log.d(
        "ACTIVE_PLAYERS_CHECK",
        game.playerList.joinToString { player ->
            player.cardsTaken.joinToString { card ->
                card.id.toString()
            }
        }
    )
    Log.d("ACTIVE_TABLE", game.tableActiveCard.toString())
    Log.d("ACTIVE_TABLE", game.temporaryPlayerTakenCard.toString())

    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PlayerUIView(game.playerList[0], viewModel)
            TableUIView(game.tableDeck, viewModel)
            PlayerUIView(game.playerList[1], viewModel)
        }
    }
}

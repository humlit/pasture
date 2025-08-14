package com.example.pastyra.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.pastyra.ui.viewmodel.GameViewModel

@Composable
fun GameStart(viewModel: GameViewModel) {
    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = Color.Gray),

        ) {
        TextButton(
            modifier = Modifier.fillMaxSize(),
            onClick = {

            }
        ) {
            Text(
                text = "Начать игру",
                color = Color.Black
            )
        }
    }
}
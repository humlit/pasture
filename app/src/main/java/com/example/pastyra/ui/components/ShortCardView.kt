package com.example.pastyra.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.SimpleCard

@Composable
fun ShortCardUIView(
    card: Card,
    isSelected: Boolean = false,
    onClick: (Card) -> Unit,
) {
    val borderColor = if (isSelected) Color.Green else Color.Transparent

    Box(
        modifier =
            Modifier
                .size(50.dp)
                .border(1.5.dp, borderColor, RoundedCornerShape(8.dp))
                .clickable { onClick(card) }
                .background(color = Color.Transparent),
    ) {
        if (card is SimpleCard) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(card.icon),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        } else if (card is Card.AnyArithmeticCard.BundleCard) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(2.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "V -> ${card.arithmeticValue}")

                if (card.point != 0) {
                    Text(text = "P -> ${card.point}")
                }

                Text(text = "S -> ${card.bundleStage}")
            }
        }
    }
}

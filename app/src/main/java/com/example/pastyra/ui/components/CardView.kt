package com.example.pastyra.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.CardBack
import com.example.pastyra.ui.components.smart.BundleButtonSmart
import com.example.pastyra.ui.viewmodel.GameViewModel

@Composable
fun CardUIView(
    viewModel: GameViewModel,
    card: Card,
    isSelected: Boolean,
    isReadyForBundle: Boolean = false,
    onClick: () -> Unit,
) {

    val borderColor = if (isSelected) Color.Green else Color.Transparent

    Column(
        modifier = Modifier
            .height(140.dp)
            .width(85.dp)
            .border(1.5.dp, borderColor, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .background(Color.Transparent)
            .clip(RoundedCornerShape(10.dp))
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isReadyForBundle) {
                BundleButtonSmart(viewModel)
            }
        }

        if (card is CardBack) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(card.icon),
                contentDescription = null,
                contentScale = ContentScale.None
            )
        }
    }
}
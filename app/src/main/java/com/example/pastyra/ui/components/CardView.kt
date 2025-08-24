package com.example.pastyra.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pastyra.domain.model.Card
import com.example.pastyra.domain.model.Rank
import com.example.pastyra.domain.model.Suit
import com.example.pastyra.domain.model.safeRank
import com.example.pastyra.domain.model.safeSuit

@Composable
fun CardUIView(
    card: Card,
    isSelected: Boolean,
    onClick:() -> Unit,
) {
    val borderColor = if(isSelected) Color.Green else Color.Transparent



    Box(
        modifier = Modifier
            .height(140.dp)
            .width(85.dp)
            .border(1.5.dp,borderColor, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(2.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = """
                        ${card.safeRank}
                        ${card.safeSuit}
                    """.trimIndent(),
                fontSize = 10.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (card.point == 0) {
                        ""
                    } else {
                        "${card.point}"
                    },
                    fontSize = 20.sp
                )
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .rotate(180f)
                    .align(alignment = Alignment.End),
                text = """
                        ${card.safeRank}
                        ${card.safeSuit}
                    """.trimIndent(),
                fontSize = 10.sp
            )
        }
    }
}
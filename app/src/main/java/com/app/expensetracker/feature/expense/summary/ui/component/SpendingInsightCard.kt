package com.app.expensetracker.feature.expense.summary.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SpendingInsightCard(message: String) {
    Box(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(Color.Black, Color.DarkGray)
                )
            )
            .padding(20.dp)
    ) {
        Text(message, color = Color.White)
    }
}

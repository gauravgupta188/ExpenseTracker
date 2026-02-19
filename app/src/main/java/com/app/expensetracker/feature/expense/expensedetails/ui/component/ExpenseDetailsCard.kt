package com.app.expensetracker.feature.expense.expensedetails.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Store
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.expensetracker.core.components.AppCard

@Composable
fun ExpenseDetailsCard(category:String,
                       date:String,
                       note:String
                       ) {
    AppCard {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            DetailItem(
                icon = Icons.Outlined.Category,
                label = "CATEGORY",
                value = category
            )
            DetailItem(
                icon = Icons.Outlined.CalendarMonth,
                label = "DATE & TIME",
                value = date
            )
            DetailItem(
                icon = Icons.Outlined.Store,
                label = "NOTE / MERCHANT",
                value = note
            )
        }
    }
}



package com.app.expensetracker.feature.expense.dashboard.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun EditBudgetIcon(onClick: () -> Unit,
iconSize : Dp = 32.dp
)
{
    Box(
        modifier = Modifier
            .size(iconSize).clickable {
               onClick()
            }
            .background(
                color = MaterialTheme.colorScheme.secondary.copy(alpha = .1f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.EditNote,
            contentDescription = "Edit Budget",
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

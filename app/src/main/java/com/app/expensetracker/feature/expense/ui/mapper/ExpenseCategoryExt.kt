package com.app.expensetracker.feature.expense.ui.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory

@Composable
fun ExpenseCategory.icon(): ImageVector =
    when (this) {
        ExpenseCategory.FOOD -> Icons.Default.Restaurant
        ExpenseCategory.TRANSPORT -> Icons.Default.DirectionsCar
        ExpenseCategory.BILLS -> Icons.Default.Receipt
        ExpenseCategory.SHOPPING -> Icons.Default.ShoppingBag
    }

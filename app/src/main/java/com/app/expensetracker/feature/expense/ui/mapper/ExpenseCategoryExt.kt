package com.app.expensetracker.feature.expense.ui.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.ui.graphics.Color
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import com.app.expensetracker.ui.theme.BrandOrange
import com.app.expensetracker.ui.theme.BrandViolet


val ExpenseCategory.displayName: String
    get() = when (this) {
        ExpenseCategory.FOOD -> "Food"
        ExpenseCategory.TRANSPORT -> "Transport"
        ExpenseCategory.RENT -> "Housing"
        ExpenseCategory.SHOPPING -> "Shopping"
        ExpenseCategory.BILLS -> "Bills"
        ExpenseCategory.OTHER -> "Other"
    }

val ExpenseCategory.icon
    get() = when (this) {
        ExpenseCategory.FOOD -> Icons.Default.Restaurant
        ExpenseCategory.TRANSPORT -> Icons.Default.DirectionsCar
        ExpenseCategory.RENT -> Icons.Default.Home
        ExpenseCategory.SHOPPING -> Icons.Default.ShoppingBag
        ExpenseCategory.BILLS -> Icons.AutoMirrored.Filled.ReceiptLong
        ExpenseCategory.OTHER -> Icons.Default.MoreHoriz
    }

val ExpenseCategory.color: Color
    get() = when (this) {
        ExpenseCategory.FOOD -> BrandViolet
        ExpenseCategory.TRANSPORT -> BrandOrange
        ExpenseCategory.RENT -> Color(0xFF4CAF50)
        ExpenseCategory.SHOPPING -> Color(0xFF03A9F4)
        ExpenseCategory.BILLS -> Color(0xFFFF9800)
        ExpenseCategory.OTHER -> Color.Gray
    }


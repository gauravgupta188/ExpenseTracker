package com.app.expensetracker.feature.expense.addexpense.state

import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import java.time.LocalDate
import java.time.LocalDateTime

data class AddExpenseUiState(
    val mode: ExpenseFormMode = ExpenseFormMode.Add,
    val amount: String = "",
    val selectedCategory: ExpenseCategory? = null,
    val selectedDate: LocalDateTime = LocalDateTime.now(),
   // val selectedTime: LocalDateTime = LocalDateTime.now(),
    val note: String = "",
    val isCategorySheetVisible: Boolean = false,

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
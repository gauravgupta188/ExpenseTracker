package com.app.expensetracker.feature.expense.addexpense.state

import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import java.time.LocalDate

data class AddExpenseUiState(
    val amount: String = "",
    val selectedCategory: ExpenseCategory? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val note: String = "",

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
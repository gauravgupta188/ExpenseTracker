package com.app.expensetracker.feature.expense.monthlyexpense.state

import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import java.time.LocalDate
import java.time.LocalDateTime

data class MonthlyExpensesUiState(
    val selectedMonth: YearMonthUiModel,

    /** Expenses grouped by date for sectioned list UI */
    val groupedExpenses: Map<LocalDateTime, List<Expense>> = emptyMap(),

    /** Flat list (optional but useful for calculations / filters later) */
    val expenses: List<Expense> = emptyList(),

    /** Aggregates */
    val totalAmount: Double = 0.0,
    val expenseCount: Int = 0,

    /** UI states */
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

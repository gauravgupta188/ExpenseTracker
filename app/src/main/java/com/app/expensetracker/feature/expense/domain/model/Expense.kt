package com.app.expensetracker.feature.expense.domain.model

import java.time.LocalDate

/**
 * Domain model for an Expense.
 * This is a pure Kotlin class with no Android dependencies.
 */
data class Expense(
    val id: String = "",
    val title: String,
    val amount: Double,
    val category: ExpenseCategory,
    val note: String?,
    val paymentMode: String,
    val date: LocalDate,
    val month: Int,
    val year: Int
)

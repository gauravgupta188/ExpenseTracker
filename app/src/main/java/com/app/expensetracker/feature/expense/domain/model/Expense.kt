package com.app.expensetracker.feature.expense.domain.model

import com.app.expensetracker.core.utils.toDisplayDateTime
import java.time.LocalDate
import java.time.LocalDateTime

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
    val date: LocalDateTime,
    val month: Int,
    val year: Int
) {
    fun formattedDateTime(): String {
        return date.toDisplayDateTime()
    }

    fun displayAmount() : String {
        return "${"%,.1f".format(amount)}"
    }
}

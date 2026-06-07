package com.app.expensetracker.feature.income.domain.model

import java.time.LocalDateTime

/**
 * Domain model for an Income entry.
 * Pure Kotlin — zero Android / framework dependencies.
 */
data class Income(
    val id: String = "",
    val title: String,
    val amount: Double,
    val category: IncomeCategory,
    val note: String?,
    val source: String,        // e.g. "Bank Transfer", "Cash", "UPI"
    val date: LocalDateTime,
    val month: Int,
    val year: Int
) {
    fun displayAmount(): String = "%,.1f".format(amount)
}

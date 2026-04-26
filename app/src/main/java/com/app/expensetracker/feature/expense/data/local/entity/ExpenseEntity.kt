package com.app.expensetracker.feature.expense.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity that mirrors the Expense domain model.
 * Stores expenses locally so the app works offline.
 * category is stored as a String (enum value) — converted via ExpenseCategoryConverter.
 * date is stored as epoch milliseconds (Long) — converted via LocalDateTimeConverter.
 */
@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey val id: String,
    val title: String,
    val amount: Double,
    val category: String,       // ExpenseCategory.value e.g. "FOOD"
    val note: String?,
    val paymentMode: String,
    val dateMillis: Long,       // LocalDateTime → epochMilli via UTC
    val month: Int,
    val year: Int,
    val syncedAt: Long = System.currentTimeMillis()
)

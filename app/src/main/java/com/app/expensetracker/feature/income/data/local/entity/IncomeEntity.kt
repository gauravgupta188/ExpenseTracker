package com.app.expensetracker.feature.income.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "income")
data class IncomeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val amount: Double,
    val category: String,       // IncomeCategory.value
    val note: String?,
    val source: String,
    val dateMillis: Long,       // LocalDateTime as epoch millis
    val month: Int,
    val year: Int,
    val syncedAt: Long = System.currentTimeMillis()
)

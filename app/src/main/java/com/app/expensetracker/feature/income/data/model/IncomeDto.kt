package com.app.expensetracker.feature.income.data.model

import com.google.firebase.Timestamp

data class IncomeDto(
    val title: String = "",
    val amount: Double = 0.0,
    val category: String = "",
    val note: String? = null,
    val source: String = "",
    val date: Timestamp = Timestamp.now(),
    val month: Int = 0,
    val year: Int = 0,
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now()
)

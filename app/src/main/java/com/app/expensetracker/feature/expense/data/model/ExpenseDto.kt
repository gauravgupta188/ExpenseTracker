package com.app.expensetracker.feature.expense.data.model

import com.google.firebase.Timestamp

data class ExpenseDto(
    val title: String = "",
    val amount: Double = 0.0,
    val category: String = "",
    val note: String? = null,
    val paymentMode: String = "",
    val date: Timestamp = Timestamp.Companion.now(),
    val month: Int = 0,
    val year: Int = 0,
    val createdAt: Timestamp = Timestamp.Companion.now(),
    val updatedAt: Timestamp = Timestamp.Companion.now()
)
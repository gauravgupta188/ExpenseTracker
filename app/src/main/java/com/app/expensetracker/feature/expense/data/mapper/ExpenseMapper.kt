package com.app.expensetracker.feature.expense.data.mapper

import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.data.model.ExpenseDto
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import com.google.firebase.Timestamp
import java.time.ZoneId

fun Expense.toDto(): ExpenseDto =
    ExpenseDto(
        title = title,
        amount = amount,
        category = category.value,
        note = note,
        paymentMode = paymentMode,
        date = Timestamp(
            date.atZone(ZoneId.systemDefault()).toInstant()
        ),
        month = month,
        year = year
    )

fun ExpenseDto.toDomain(id: String): Expense =
    Expense(
        id = id,
        title = title,
        amount = amount,
        category = ExpenseCategory.fromValue(category),
        note = note,
        paymentMode = paymentMode,
        date = date.toDate()
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime(),
        month = month,
        year = year
    )

package com.app.expensetracker.feature.expense.data.mapper

import com.app.expensetracker.feature.expense.data.local.entity.ExpenseEntity
import com.app.expensetracker.feature.expense.domain.model.Expense
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import java.time.Instant
import java.time.ZoneId

// ── Domain → Entity (before writing to Room) ──────────────────────────────

fun Expense.toEntity(): ExpenseEntity =
    ExpenseEntity(
        id = id,
        title = title,
        amount = amount,
        category = category.value,
        note = note,
        paymentMode = paymentMode,
        dateMillis = date
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli(),
        month = month,
        year = year
    )

// ── Entity → Domain (after reading from Room) ─────────────────────────────

fun ExpenseEntity.toDomain(): Expense =
    Expense(
        id = id,
        title = title,
        amount = amount,
        category = ExpenseCategory.fromValue(category),
        note = note,
        paymentMode = paymentMode,
        date = Instant.ofEpochMilli(dateMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime(),
        month = month,
        year = year
    )

// ── List convenience helpers ───────────────────────────────────────────────

fun List<ExpenseEntity>.toDomainList(): List<Expense> = map { it.toDomain() }

fun List<Expense>.toEntityList(): List<ExpenseEntity> = map { it.toEntity() }

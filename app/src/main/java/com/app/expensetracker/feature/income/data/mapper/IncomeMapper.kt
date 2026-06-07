package com.app.expensetracker.feature.income.data.mapper

import com.app.expensetracker.feature.income.data.local.entity.IncomeEntity
import com.app.expensetracker.feature.income.data.model.IncomeDto
import com.app.expensetracker.feature.income.domain.model.Income
import com.app.expensetracker.feature.income.domain.model.IncomeCategory
import java.time.Instant
import java.time.ZoneId

// ── Domain → Entity ────────────────────────────────────────────────────────

fun Income.toEntity(): IncomeEntity = IncomeEntity(
    id = id,
    title = title,
    amount = amount,
    category = category.value,
    note = note,
    source = source,
    dateMillis = date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
    month = month,
    year = year
)

// ── Entity → Domain ────────────────────────────────────────────────────────

fun IncomeEntity.toDomain(): Income = Income(
    id = id,
    title = title,
    amount = amount,
    category = IncomeCategory.fromValue(category),
    note = note,
    source = source,
    date = Instant.ofEpochMilli(dateMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime(),
    month = month,
    year = year
)

// ── Domain → Firestore DTO ─────────────────────────────────────────────────

fun Income.toDto(): IncomeDto = IncomeDto(
    title = title,
    amount = amount,
    category = category.value,
    note = note,
    source = source,
    month = month,
    year = year
)

// ── Firestore DTO → Domain ─────────────────────────────────────────────────

fun IncomeDto.toDomain(id: String): Income = Income(
    id = id,
    title = title,
    amount = amount,
    category = IncomeCategory.fromValue(category),
    note = note,
    source = source,
    date = date.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
    month = month,
    year = year
)

// ── List helpers ───────────────────────────────────────────────────────────

fun List<IncomeEntity>.toDomainList(): List<Income> = map { it.toDomain() }
fun List<Income>.toEntityList(): List<IncomeEntity> = map { it.toEntity() }

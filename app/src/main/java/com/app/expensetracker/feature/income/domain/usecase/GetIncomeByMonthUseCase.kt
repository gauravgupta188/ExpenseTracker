package com.app.expensetracker.feature.income.domain.usecase

import com.app.expensetracker.feature.income.domain.model.Income
import com.app.expensetracker.feature.income.domain.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow

class GetIncomeByMonthUseCase(private val repository: IncomeRepository) {
    operator fun invoke(year: Int, month: Int): Flow<List<Income>> =
        repository.getIncomeByMonth(year, month)
}
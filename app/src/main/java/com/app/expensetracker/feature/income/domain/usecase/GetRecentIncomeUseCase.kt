package com.app.expensetracker.feature.income.domain.usecase

import com.app.expensetracker.feature.income.domain.model.Income
import com.app.expensetracker.feature.income.domain.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow

class GetRecentIncomeUseCase(private val repository: IncomeRepository) {
    operator fun invoke(limit: Int = 5): Flow<List<Income>> =
        repository.getRecentIncome(limit)
}
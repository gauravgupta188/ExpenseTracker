package com.app.expensetracker.feature.income.domain.usecase

import com.app.expensetracker.feature.income.domain.model.Income
import com.app.expensetracker.feature.income.domain.repository.IncomeRepository

class UpdateIncomeUseCase(private val repository: IncomeRepository) {
    suspend operator fun invoke(income: Income) = repository.updateIncome(income)
}
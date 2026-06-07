package com.app.expensetracker.feature.income.domain.usecase

import com.app.expensetracker.feature.income.domain.model.Income
import com.app.expensetracker.feature.income.domain.repository.IncomeRepository

class AddIncomeUseCase(private val repository: IncomeRepository) {
    suspend operator fun invoke(income: Income) = repository.addIncome(income)
}
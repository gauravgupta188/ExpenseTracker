package com.app.expensetracker.feature.income.domain.usecase

import com.app.expensetracker.feature.income.domain.repository.IncomeRepository

class DeleteIncomeUseCase(private val repository: IncomeRepository) {
    suspend operator fun invoke(incomeId: String) = repository.deleteIncome(incomeId)
}
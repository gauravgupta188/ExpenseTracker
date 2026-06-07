package com.app.expensetracker.feature.income.domain.repository

import com.app.expensetracker.feature.income.domain.model.Income
import kotlinx.coroutines.flow.Flow

interface IncomeRepository {

    suspend fun addIncome(income: Income)

    suspend fun updateIncome(income: Income)

    suspend fun deleteIncome(incomeId: String)

    fun getIncomeByMonth(year: Int, month: Int): Flow<List<Income>>

    fun getRecentIncome(limit: Int = 5): Flow<List<Income>>

    fun observeIncomeById(incomeId: String): Flow<Income>
}

package com.app.expensetracker.feature.expense.domain.usecase
import com.app.expensetracker.feature.expense.domain.repository.ExpenseRepository
import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetMonthlySummaryUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {

    operator fun invoke(
        year: Int,
        month: Int
    ): Flow<List<CategorySummaryUiModel>> {

        return expenseRepository
            .getExpensesByMonth(year, month)
            .map { expenses ->

                expenses
                    .groupBy { it.category }
                    .map { (category, list) ->
                        CategorySummaryUiModel(
                            category = category,
                            spentAmount = list.sumOf { it.amount },
                            budgetAmount = null // plug later
                        )
                    }
                    .sortedByDescending { it.spentAmount }
            }
    }
}

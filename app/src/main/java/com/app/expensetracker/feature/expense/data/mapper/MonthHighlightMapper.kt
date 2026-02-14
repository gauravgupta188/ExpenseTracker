package com.app.expensetracker.feature.expense.data.mapper

import com.app.expensetracker.feature.expense.domain.model.BudgetInsightType
import com.app.expensetracker.feature.expense.domain.model.BudgetInsightUi
import com.app.expensetracker.feature.expense.domain.model.MonthComparisonUi
import com.app.expensetracker.feature.expense.domain.model.MonthHighlightsUi
import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel
import com.app.expensetracker.feature.expense.ui.mapper.icon
import kotlin.math.abs

fun mapToMonthHighlightsUi(
    currentMonthCategories: List<CategorySummaryUiModel>,
    previousMonthTotal: Double,
    currentMonthTotal: Double
): MonthHighlightsUi {

    val topCategories =
        currentMonthCategories
            .sortedByDescending { it.spentAmount }
            .take(3)
            .map {
                CategorySummaryUiModel(
                    category = it.category,
                    spentAmount = it.spentAmount,
                    budgetAmount = it.budgetAmount
                )
            }

    val budgetInsights =
        currentMonthCategories
            .filter { it.budgetAmount != null }
            .mapNotNull { category ->
                val budget = category.budgetAmount!!
                when {
                    category.spentAmount > budget ->
                        BudgetInsightUi(
                            type = BudgetInsightType.OVER_BUDGET,
                            message =
                                "${category.category.name} exceeded budget by ₹${(category.spentAmount - budget).toInt()}"
                        )

                    category.spentAmount <= budget ->
                        BudgetInsightUi(
                            type = BudgetInsightType.WITHIN_BUDGET,
                            message =
                                "${category.category.name} remained within budget"
                        )

                    else -> null
                }
            }
            .take(2)

    val percentageChange =
        if (previousMonthTotal == 0.0) 0
        else (((currentMonthTotal - previousMonthTotal) / previousMonthTotal) * 100).toInt()

    val comparison = MonthComparisonUi(
        percentageChange = abs(percentageChange),
        isIncrease = percentageChange > 0,
        summaryText =
            if (percentageChange > 0)
                "You spent ${percentageChange}% more than last month"
            else
                "You spent ${abs(percentageChange)}% less than last month"
    )

    return MonthHighlightsUi(
        topCategories = topCategories,
        budgetInsights = budgetInsights,
        comparison = comparison
    )
}

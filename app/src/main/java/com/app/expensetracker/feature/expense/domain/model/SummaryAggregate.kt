package com.app.expensetracker.feature.expense.domain.model

import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel

 data class SummaryAggregate(
  //  val expenses: List<Expense>,
    val categories: List<CategorySummaryUiModel>,
    val monthlyBudget: Double?,
    val totalAmount: Double,
    val remainingBudget: Double,
)
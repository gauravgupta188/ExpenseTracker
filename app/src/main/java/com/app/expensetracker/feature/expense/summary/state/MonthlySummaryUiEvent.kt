package com.app.expensetracker.feature.expense.summary.state

import com.app.expensetracker.feature.expense.addexpense.state.AddExpenseUiEvent
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel
import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel

sealed class MonthlySummaryUiEvent {
    object OnBackClicked : MonthlySummaryUiEvent()
    object OnAddExpenseClicked : MonthlySummaryUiEvent()
    object OnMonthSelectorClicked : MonthlySummaryUiEvent()
    data class OnMonthSelected(val month: YearMonthUiModel) : MonthlySummaryUiEvent()
    data class OnSaveBudget(val amount: Double) : MonthlySummaryUiEvent()
    object BudgetEditClicked : MonthlySummaryUiEvent()
    object CloseBudgetSheet : MonthlySummaryUiEvent()

    data class OnCategoryClicked(
        val category: CategorySummaryUiModel
    ) : MonthlySummaryUiEvent()

    data class OnSaveCategoryBudget(
        val amount: Double
    ) : MonthlySummaryUiEvent()

    object OnDismissBottomSheet : MonthlySummaryUiEvent()

}


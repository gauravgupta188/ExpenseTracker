package com.app.expensetracker.feature.expense.addexpense.state

sealed class ExpenseFormMode {
    object Add : ExpenseFormMode()
    data class Edit(val expenseId: String) : ExpenseFormMode()
}

package com.app.expensetracker.feature.auth.resetpassword.state

import com.app.expensetracker.feature.expense.domain.model.Expense


sealed class ResetPasswordUiEffect {
    object NavigateBack : ResetPasswordUiEffect()
    data class ShowSnackBar(val message: String) : ResetPasswordUiEffect()

}
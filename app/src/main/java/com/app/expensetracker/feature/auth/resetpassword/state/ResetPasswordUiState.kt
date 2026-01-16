package com.app.expensetracker.feature.auth.resetpassword.state

data class ResetPasswordUiState (
    val email: String = "",
    val emailError: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
)
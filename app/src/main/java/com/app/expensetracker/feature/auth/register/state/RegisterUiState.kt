package com.app.expensetracker.feature.auth.register.state

data class RegisterUiState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val isTermsAccepted: Boolean = false,
    val fullNameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

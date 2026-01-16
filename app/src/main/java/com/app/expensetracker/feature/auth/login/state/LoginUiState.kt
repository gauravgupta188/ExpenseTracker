package com.app.expensetracker.feature.auth.login.state

data class LoginUiState(
    val email: String = "gaurav@yopmail.com",
    val password: String = "Qwerty@11",

    val emailError: String? = null,
    val passwordError: String? = null,

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

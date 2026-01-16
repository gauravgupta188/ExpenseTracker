package com.app.expensetracker.feature.auth.login.state

sealed interface LoginUiEvent {
    data class OnEmailChanged(val value: String) : LoginUiEvent
    data class OnPasswordChanged(val value: String) : LoginUiEvent
    object OnLoginClicked : LoginUiEvent
    object  OnSignupClicked : LoginUiEvent
    object OnForgotPasswordClicked : LoginUiEvent
    object OnGoogleLoginClicked : LoginUiEvent
    object OnFacebookLoginClicked : LoginUiEvent
    object OnGuestLoginClicked : LoginUiEvent
}

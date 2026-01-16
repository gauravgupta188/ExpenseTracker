package com.app.expensetracker.feature.auth.login.state


sealed interface LoginUiEffect {
    object LaunchGoogleSignIn : LoginUiEffect
    data class ShowSnackBar(val message: String) : LoginUiEffect
    object NavigateToHome : LoginUiEffect
}

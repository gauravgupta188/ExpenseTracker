package com.app.expensetracker.feature.auth.register.state


sealed interface RegisterUiEvent {
    data class OnFullNameChanged(val value: String) : RegisterUiEvent
    data class OnEmailChanged(val value: String) : RegisterUiEvent
    data class OnPasswordChanged(val value: String) : RegisterUiEvent
    data class OnTermsChecked(val checked: Boolean) : RegisterUiEvent
    object OnSignUpClicked : RegisterUiEvent

}

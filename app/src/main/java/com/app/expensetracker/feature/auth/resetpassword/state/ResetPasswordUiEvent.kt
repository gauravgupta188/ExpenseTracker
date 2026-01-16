package com.app.expensetracker.feature.auth.resetpassword.state

sealed interface ResetPasswordUiEvent {

    data class OnEmailChanged(val value: String) : ResetPasswordUiEvent
    object OnSendResetLinkClicked : ResetPasswordUiEvent
    object OnTryAgainClicked : ResetPasswordUiEvent
}

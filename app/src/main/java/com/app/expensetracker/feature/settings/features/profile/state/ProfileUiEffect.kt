package com.app.expensetracker.feature.settings.features.profile.state

sealed interface ProfileUiEffect {

    data object NavigateBack : ProfileUiEffect

    data object LogoutSuccess : ProfileUiEffect

    data class ShowError(val message: String) : ProfileUiEffect
}
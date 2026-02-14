package com.app.expensetracker.feature.settings.state

sealed interface SettingsUiEffect {

    // Navigation
    data object NavigateBack : SettingsUiEffect
    data object NavigateToProfile : SettingsUiEffect
    data object NavigateToSubscription : SettingsUiEffect
    data object NavigateToCurrency : SettingsUiEffect
    data object NavigateToPasscode : SettingsUiEffect
    data object NavigateToSupport : SettingsUiEffect

    // Dialogs / system
    data object LogoutSuccess : SettingsUiEffect
}

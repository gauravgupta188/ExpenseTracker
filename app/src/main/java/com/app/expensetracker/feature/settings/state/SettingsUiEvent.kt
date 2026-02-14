package com.app.expensetracker.feature.settings.state

sealed interface SettingsUiEvent {

    // Navigation
    data object BackClicked : SettingsUiEvent
    data object ProfileClicked : SettingsUiEvent
    data object SubscriptionClicked : SettingsUiEvent
    data object CurrencyClicked : SettingsUiEvent
    data object PasscodeClicked : SettingsUiEvent
    data object SupportClicked : SettingsUiEvent
    data object LogoutClicked : SettingsUiEvent
    data object DismissLogoutDialog : SettingsUiEvent
    data object ConfirmLogout : SettingsUiEvent

    // Toggles
    data class NotificationsToggled(val enabled: Boolean) : SettingsUiEvent
    data class DarkModeToggled(val enabled: Boolean) : SettingsUiEvent
}

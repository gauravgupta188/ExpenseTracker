package com.app.expensetracker.feature.settings.state

import com.app.expensetracker.feature.expense.addexpense.state.AddExpenseUiEvent
import java.time.LocalDate

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
    data object DismissCurrencySheet : SettingsUiEvent
  //  data object ConfirmCurrencyChange : SettingsUiEvent
    data class ConfirmCurrencyChange(val currency: String) : SettingsUiEvent




    // Toggles
    data class NotificationsToggled(val enabled: Boolean) : SettingsUiEvent
    data class DarkModeToggled(val enabled: Boolean) : SettingsUiEvent
}

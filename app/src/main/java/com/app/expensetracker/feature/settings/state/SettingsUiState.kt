package com.app.expensetracker.feature.settings.state

data class SettingsUiState(
    val isLoading: Boolean = false,

    val showLogoutDialog: Boolean = false,

    // Account
    val isProUser: Boolean = false,
    val subscriptionLabel: String = "Free Tier active",

    // Preferences
    val defaultCurrency: String = "USD ($)",
    val notificationsEnabled: Boolean = true,
    val darkModeEnabled: Boolean = false,

    // App
    val appVersion: String = "Expense Tracker v2.4.0"
)

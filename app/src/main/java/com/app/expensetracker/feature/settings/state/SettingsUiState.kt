package com.app.expensetracker.feature.settings.state

import com.app.expensetracker.feature.settings.domain.model.CurrencyItem
import com.app.expensetracker.feature.settings.domain.model.CurrencyProvider

data class SettingsUiState(
    val isLoading: Boolean = false,

    val showLogoutDialog: Boolean = false,

    // Account
    val isProUser: Boolean = false,
    val subscriptionLabel: String = "Free Tier active",

    // Preferences
    val defaultCurrency: CurrencyItem = CurrencyProvider.getCurrencyByCode("USD"),
    val notificationsEnabled: Boolean = true,
    val darkModeEnabled: Boolean = false,
    val showCurrencySheet : Boolean = false,

    // App
    val appVersion: String = "Expense Tracker v1.0.1"

    
)

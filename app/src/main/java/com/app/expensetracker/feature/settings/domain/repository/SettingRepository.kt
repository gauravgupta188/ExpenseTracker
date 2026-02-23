package com.app.expensetracker.feature.settings.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val defaultCurrency: Flow<String>

    suspend fun setDefaultCurrency(code: String)

    val isDarkMode: Flow<Boolean>

    suspend fun setDarkMode(enabled: Boolean)
}

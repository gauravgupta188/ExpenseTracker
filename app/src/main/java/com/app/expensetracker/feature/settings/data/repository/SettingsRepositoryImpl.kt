package com.app.expensetracker.feature.settings.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.app.expensetracker.feature.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    private val DEFAULT_CURRENCY =
        stringPreferencesKey("default_currency")

    private val DARK_MODE =
        booleanPreferencesKey("dark_mode")

    override val isDarkMode: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[DARK_MODE] ?: false
        }

    override suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE] = enabled
        }
    }

    override val defaultCurrency: Flow<String> =
        dataStore.data.map { preferences ->
            preferences[DEFAULT_CURRENCY] ?: "USD"
        }

    override suspend fun setDefaultCurrency(code: String) {
        dataStore.edit { preferences ->
            preferences[DEFAULT_CURRENCY] = code
        }
    }
}
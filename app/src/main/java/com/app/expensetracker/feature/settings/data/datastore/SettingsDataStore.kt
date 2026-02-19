package com.app.expensetracker.feature.settings.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.core.DataStore

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

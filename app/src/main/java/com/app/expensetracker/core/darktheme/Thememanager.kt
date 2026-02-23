package com.app.expensetracker.core.darktheme

import com.app.expensetracker.feature.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeManager @Inject constructor(
    settingsRepository: SettingsRepository
) {

    val isDarkMode: StateFlow<Boolean> =
        settingsRepository.isDarkMode
            .stateIn(
                CoroutineScope(SupervisorJob() + Dispatchers.IO),
                SharingStarted.Eagerly,
                false
            )
}
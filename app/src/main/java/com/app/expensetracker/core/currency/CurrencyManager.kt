package com.app.expensetracker.core.currency

import com.app.expensetracker.feature.settings.domain.model.CurrencyItem
import com.app.expensetracker.feature.settings.domain.model.CurrencyProvider
import com.app.expensetracker.feature.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyManager @Inject constructor(
    private val settingsRepository: SettingsRepository
) {

    val currency: Flow<CurrencyItem> =
        settingsRepository.defaultCurrency
            .map { CurrencyProvider.getCurrencyByCode(it) }
}

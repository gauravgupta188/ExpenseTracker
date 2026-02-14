package com.app.expensetracker.feature.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.expensetracker.feature.auth.domain.repository.AuthRepository
import com.app.expensetracker.feature.settings.state.SettingsUiEffect
import com.app.expensetracker.feature.settings.state.SettingsUiEvent
import com.app.expensetracker.feature.settings.state.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository
    // Later: DataStore, AuthRepository, BillingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<SettingsUiEffect>()
    val uiEffect: SharedFlow<SettingsUiEffect> = _uiEffect

    fun onEvent(event: SettingsUiEvent) {
        when (event) {

            // ───── Navigation ─────
            SettingsUiEvent.BackClicked ->
                emitEffect(SettingsUiEffect.NavigateBack)

            SettingsUiEvent.ProfileClicked ->
                emitEffect(SettingsUiEffect.NavigateToProfile)

            SettingsUiEvent.SubscriptionClicked ->
                emitEffect(SettingsUiEffect.NavigateToSubscription)

            SettingsUiEvent.CurrencyClicked ->
                emitEffect(SettingsUiEffect.NavigateToCurrency)

            SettingsUiEvent.PasscodeClicked ->
                emitEffect(SettingsUiEffect.NavigateToPasscode)

            SettingsUiEvent.SupportClicked ->
                emitEffect(SettingsUiEffect.NavigateToSupport)

            SettingsUiEvent.LogoutClicked ->
            {
                _uiState.update {
                    it.copy(showLogoutDialog = true)
                }
            }

            SettingsUiEvent.DismissLogoutDialog -> {
                _uiState.update {
                    it.copy(showLogoutDialog = false)
                }
            }

            // ───── Toggles ─────
            is SettingsUiEvent.NotificationsToggled -> {
                _uiState.update {
                    it.copy(notificationsEnabled = event.enabled)
                }
                // Persist later via DataStore
            }

            is SettingsUiEvent.DarkModeToggled -> {
                _uiState.update {
                    it.copy(darkModeEnabled = event.enabled)
                }
                // Persist + apply theme later
            }

            SettingsUiEvent.ConfirmLogout -> {
                logout()
                _uiState.update { it.copy(showLogoutDialog = false) }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            try {
                authRepository.logout()
                emitEffect(SettingsUiEffect.LogoutSuccess)
            } catch (e: Exception) {
                // Optional: emit error snackbar
            }
        }
    }

    private fun emitEffect(effect: SettingsUiEffect) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }
}

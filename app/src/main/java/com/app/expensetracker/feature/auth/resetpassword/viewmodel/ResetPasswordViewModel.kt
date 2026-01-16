package com.app.expensetracker.feature.auth.resetpassword.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.expensetracker.feature.auth.register.state.RegisterUiEvent
import com.app.expensetracker.feature.auth.register.state.RegisterUiState
import com.app.expensetracker.feature.auth.resetpassword.state.ResetPasswordUiEvent
import com.app.expensetracker.feature.auth.resetpassword.state.ResetPasswordUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ResetPasswordViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ResetPasswordUiState())
    val uiState: StateFlow<ResetPasswordUiState> = _uiState

    fun onEvent(event: ResetPasswordUiEvent) {
        when (event) {
            is ResetPasswordUiEvent.OnEmailChanged -> {
                _uiState.update { it.copy(email = event.value) }
            }
            ResetPasswordUiEvent.OnSendResetLinkClicked -> {sendResetLink()}
            ResetPasswordUiEvent.OnTryAgainClicked -> {}
        }
    }

    private fun sendResetLink() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            // TODO: Firebase email/password login

            _uiState.update { it.copy(isLoading = false) }
        }
    }
}

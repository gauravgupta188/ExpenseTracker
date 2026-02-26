package com.app.expensetracker.feature.auth.resetpassword.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.expensetracker.feature.auth.domain.usecases.ResetPasswordUseCase
import com.app.expensetracker.feature.auth.login.state.LoginUiEffect
import com.app.expensetracker.feature.auth.register.state.RegisterUiEvent
import com.app.expensetracker.feature.auth.register.state.RegisterUiState
import com.app.expensetracker.feature.auth.resetpassword.state.ResetPasswordUiEffect
import com.app.expensetracker.feature.auth.resetpassword.state.ResetPasswordUiEvent
import com.app.expensetracker.feature.auth.resetpassword.state.ResetPasswordUiState
import com.app.expensetracker.feature.auth.resetpassword.ui.ResetPasswordScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel(

) {

    private val _uiState = MutableStateFlow(ResetPasswordUiState())
    val uiState: StateFlow<ResetPasswordUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<ResetPasswordUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()
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

            runCatching {
                resetPasswordUseCase(
                    email = _uiState.value.email,
                )
            }.onSuccess {
                Log.d("Success","Link successful send")
                _uiState.update { it.copy(isLoading = false) }
                _uiEffect.emit(ResetPasswordUiEffect.ShowSnackBar("Password reset link sent"))
                _uiEffect.emit(ResetPasswordUiEffect.NavigateBack)

            }.onFailure {
                _uiState.update { it.copy(error = it.error, isLoading = false) }
            }


            _uiState.update { it.copy(isLoading = false) }
        }
    }
}

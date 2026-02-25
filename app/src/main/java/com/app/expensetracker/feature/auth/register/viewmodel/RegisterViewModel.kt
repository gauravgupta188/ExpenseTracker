package com.app.expensetracker.feature.auth.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.expensetracker.feature.auth.domain.usecases.RegisterUserUseCase
import com.app.expensetracker.feature.auth.register.state.RegisterUiEvent
import com.app.expensetracker.feature.auth.register.state.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow

import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUser: RegisterUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState
    private val _navigationEffect = MutableSharedFlow<Unit>()
    val navigationEffect = _navigationEffect.asSharedFlow()

    fun onEvent(event: RegisterUiEvent) {
        when (event) {

            is RegisterUiEvent.OnEmailChanged -> {
                _uiState.update { it.copy(email = event.value) }
            }

            is RegisterUiEvent.OnFullNameChanged -> {
                _uiState.update { it.copy(fullName = event.value) }
            }

            is RegisterUiEvent.OnPasswordChanged -> {
                _uiState.update { it.copy(password = event.value) }
            }

            RegisterUiEvent.OnSignUpClicked -> {
                register()
            }

            is RegisterUiEvent.OnTermsChecked -> {
                _uiState.update { it.copy(isTermsAccepted = event.checked) }
            }
        }
    }

    private fun register() = viewModelScope.launch {

        _uiState.update { it.copy(isLoading = true, error = null) }

        runCatching {
            registerUser(email = _uiState.value.email, password = _uiState.value.password, displayName = _uiState.value.fullName)
        }.onSuccess {
            _navigationEffect.emit(Unit)
            _uiState.update { it.copy(isLoading = false) }
        }.onFailure {
            _uiState.update { it.copy(error = it.error, isLoading = false) }
        }

        // TODO: Firebase email/password login

        _uiState.update { it.copy(isLoading = false) }

    }
}

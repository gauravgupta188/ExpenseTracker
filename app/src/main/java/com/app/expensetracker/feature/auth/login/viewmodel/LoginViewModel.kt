package com.app.expensetracker.feature.auth.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.expensetracker.core.utils.Validation
import com.app.expensetracker.feature.auth.domain.usecases.LoginUserUseCase
import com.app.expensetracker.feature.auth.domain.usecases.LoginWithGoogleUseCase
import com.app.expensetracker.feature.auth.login.state.LoginUiEffect
import com.app.expensetracker.feature.auth.login.state.LoginUiEvent
import com.app.expensetracker.feature.auth.login.state.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUser: LoginUserUseCase,
    private val loginWithGoogleUseCase: LoginWithGoogleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<LoginUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()


    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.OnEmailChanged -> {
                _uiState.update { it.copy(email = event.value, emailError = null) }
            }

            is LoginUiEvent.OnPasswordChanged -> {
                _uiState.update { it.copy(password = event.value, passwordError = null) }
            }

            LoginUiEvent.OnLoginClicked -> {
                validateLogin()
            }

            LoginUiEvent.OnGuestLoginClicked -> {
                guestLogin()
            }

            LoginUiEvent.OnFacebookLoginClicked -> TODO()
            LoginUiEvent.OnForgotPasswordClicked -> TODO()
            LoginUiEvent.OnGoogleLoginClicked -> {

                viewModelScope.launch {
                    _uiEffect.emit(LoginUiEffect.LaunchGoogleSignIn)

                }
            }

            LoginUiEvent.OnSignupClicked -> {}

        }
    }


    fun handleGoogleIdToken(idToken: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            runCatching {
                loginWithGoogleUseCase(idToken)
            }.onSuccess {
                _uiEffect.emit(LoginUiEffect.NavigateToHome)
            }.onFailure {
                _uiState.update { it.copy(isLoading = false) }
                _uiEffect.emit(
                    LoginUiEffect.ShowSnackBar("Google sign-in failed")
                )
            }
        }
    }

    fun onGoogleSignInError() {
        viewModelScope.launch {
            _uiEffect.emit(
                LoginUiEffect.ShowSnackBar("Google sign-in cancelled or failed")
            )
        }
    }



    private fun validateLogin() {
        val state = _uiState.value

        val emailError = Validation.validateEmail(state.email)
        val passwordError = Validation.validatePassword(state.password)

        if (emailError != null || passwordError != null) {
            _uiState.update {
                it.copy(
                    emailError = emailError,
                    passwordError = passwordError
                )
            }
            return
        }

        login()
    }

    private fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            runCatching {
                loginUser(email = _uiState.value.email, password = _uiState.value.password)
            }.onSuccess {
                _uiState.update {
                    it.copy(isLoading = false)
                }
                _uiEffect.emit(LoginUiEffect.NavigateToHome)

            }.onFailure { throwable ->
                _uiState.update { it.copy(errorMessage = throwable.message, isLoading = false) }
                _uiEffect.emit(LoginUiEffect.ShowSnackBar(throwable.message ?: "Login Failed"))
            }
        }
    }




    private fun guestLogin() {
        // TODO: Firebase anonymous login
    }
}

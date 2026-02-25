package com.app.expensetracker.feature.settings.features.profile.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.expensetracker.feature.auth.domain.repository.AuthRepository
import com.app.expensetracker.feature.settings.features.profile.state.ProfileUiEffect
import com.app.expensetracker.feature.settings.features.profile.state.ProfileUiEvent
import com.app.expensetracker.feature.settings.features.profile.state.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<ProfileUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    init {
        loadUser()
    }

    fun onEvent(event: ProfileUiEvent) {
        when (event) {

            ProfileUiEvent.BackClicked ->
                emitEffect(ProfileUiEffect.NavigateBack)

            ProfileUiEvent.EditClicked ->
                _uiState.update {
                    it.copy(isEditing = true)
                }

            is ProfileUiEvent.NameChanged ->
                _uiState.update {
                    it.copy(fullName = event.value)
                }

            ProfileUiEvent.SaveClicked ->
                saveProfile()

            ProfileUiEvent.LogoutClicked ->
                logout()

            is ProfileUiEvent.PhotoSelected -> {
                uploadPhoto(event.uri)
            }
        }
    }

    private fun uploadPhoto(uri: Uri) {

        viewModelScope.launch {

            try {
                _uiState.update { it.copy(isLoading = true) }

                val url = authRepository.uploadProfileImage(uri)

                authRepository.updateProfilePhoto(url)

                _uiState.update {
                    it.copy(
                        imageUrl = url,
                        isLoading = false
                    )
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
                emitEffect(ProfileUiEffect.ShowError("Upload failed"))
            }
        }
    }

    private fun loadUser() {
        val user = authRepository.getCurrentUser()

        _uiState.update {
            it.copy(
                fullName = user?.displayName ?: "",
                email = user?.email ?: ""
            )
        }
    }

    private fun saveProfile() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                authRepository.updateDisplayName(_uiState.value.fullName)

                _uiState.update {
                    it.copy(
                        isEditing = false,
                        isLoading = false
                    )
                }

            } catch (e: Exception) {
                emitEffect(ProfileUiEffect.ShowError("Update failed"))
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            emitEffect(ProfileUiEffect.LogoutSuccess)
        }
    }

    private fun emitEffect(effect: ProfileUiEffect) {
        viewModelScope.launch {
            _uiEffect.emit(effect)
        }
    }
}
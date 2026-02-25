package com.app.expensetracker.feature.settings.features.profile.state

data class ProfileUiState(
    val fullName: String = "",
    val email: String = "",
    val imageUrl: String? = null,
    val isEditing: Boolean = false,
    val isLoading: Boolean = false
)
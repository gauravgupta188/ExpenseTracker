package com.app.expensetracker.feature.settings.features.profile.state

import android.net.Uri

sealed interface ProfileUiEvent {

    data object BackClicked : ProfileUiEvent

    data object EditClicked : ProfileUiEvent

    data class NameChanged(val value: String) : ProfileUiEvent

    data object SaveClicked : ProfileUiEvent

    data object LogoutClicked : ProfileUiEvent

    data class PhotoSelected(val uri: Uri) : ProfileUiEvent
}
package com.app.expensetracker.feature.settings.features.profile.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.expensetracker.core.components.AppButton
import com.app.expensetracker.feature.settings.features.profile.state.ProfileUiEvent
import com.app.expensetracker.feature.settings.features.profile.state.ProfileUiState
import com.app.expensetracker.feature.settings.features.profile.ui.components.ProfileAvatar
import com.app.expensetracker.feature.settings.features.profile.ui.components.ProfileDisabledField
import com.app.expensetracker.feature.settings.features.profile.ui.components.ProfileEditableField
import com.app.expensetracker.feature.settings.features.profile.ui.components.ProfileLabel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onEvent: (ProfileUiEvent) -> Unit,
) {
// Storage is paid feature in firebase, We will avoid this for now.
  /*  val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            onEvent(ProfileUiEvent.PhotoSelected(it))
        }
    }*/

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Profile Settings") },
                navigationIcon = {
                    IconButton(onClick = { onEvent(ProfileUiEvent.BackClicked) }) {
                        Icon(Icons.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            ProfileAvatar(
                name = uiState.fullName,
                imageUrl = uiState.imageUrl,
                onEditClick = {}// {  launcher.launch("image/*") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = uiState.fullName,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )


            Spacer(modifier = Modifier.height(32.dp))

        /*    ProfileLabel("USERNAME")

            ProfileDisabledField(
                value = "@${uiState.email.lowercase().replace(" ", "")}"
            )

             Spacer(modifier = Modifier.height(24.dp))
*/


            ProfileLabel("FULL NAME")

            ProfileEditableField(
                value = uiState.fullName,
                onValueChange = {
                    onEvent(ProfileUiEvent.NameChanged(it))
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            ProfileLabel("EMAIL ADDRESS")

            ProfileDisabledField(
                value = uiState.email
            )

            Spacer(modifier = Modifier.height(32.dp))

            //PasswordSecurityCard()

            Spacer(modifier = Modifier.height(32.dp))

            AppButton(
                onClick = { onEvent(ProfileUiEvent.SaveClicked) },
                text = "Update Profile",
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
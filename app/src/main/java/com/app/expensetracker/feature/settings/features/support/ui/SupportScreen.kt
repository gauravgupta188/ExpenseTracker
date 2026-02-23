package com.app.expensetracker.feature.settings.features.support.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.settings.features.support.helper.openEmail
import com.app.expensetracker.feature.settings.features.support.helper.openPlayStore
import com.app.expensetracker.feature.settings.features.support.ui.components.AppVersionInfo
import com.app.expensetracker.feature.settings.features.support.ui.components.SupportItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help & Feedback") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            SupportItem(
                title = "Send Feedback",
                subtitle = "Share your suggestions or ideas",
                onClick = {
                    openEmail(
                        context = context,
                        subject = "Feedback - Expense Tracker"
                    )
                }
            )

            SupportItem(
                title = "Report a Bug",
                subtitle = "Tell us what went wrong",
                onClick = {
                    openEmail(
                        context = context,
                        subject = "Bug Report - Expense Tracker"
                    )
                }
            )

            SupportItem(
                title = "Rate the App",
                subtitle = "Support us on Play Store",
                onClick = {
                    openPlayStore(context)
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            AppVersionInfo()
        }
    }
}
package com.app.expensetracker.feature.settings.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.app.expensetracker.core.utils.APP_VERSION
import com.app.expensetracker.feature.settings.state.SettingsUiEvent
import com.app.expensetracker.feature.settings.state.SettingsUiState
import com.app.expensetracker.feature.settings.ui.component.CurrencyBottomSheet
import com.app.expensetracker.feature.settings.ui.component.LogoutConfirmationDialog
import com.app.expensetracker.feature.settings.ui.component.PremiumUpgradeCard
import com.app.expensetracker.feature.settings.ui.component.SettingsNavigationItem
import com.app.expensetracker.feature.settings.ui.component.SettingsSectionHeader
import com.app.expensetracker.feature.settings.ui.component.SettingsToggleItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onEvent: (SettingsUiEvent) -> Unit,
    uiState: SettingsUiState,
    onBackClick: () -> Unit
    /* onBackClick: () -> Unit,
     onUpgradeClick: () -> Unit,
     onProfileClick: () -> Unit,
     onSubscriptionClick: () -> Unit,
     onCurrencyClick: () -> Unit,
     onPasscodeClick: () -> Unit,
     onSupportClick: () -> Unit,
     onLogoutClick: () -> Unit,*/
    //notificationsEnabled: Boolean,
    //onNotificationsToggle: (Boolean) -> Unit,
   // darkModeEnabled: Boolean,
   // onDarkModeToggle: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {

            /* ───── Premium ───── */
            item {
                PremiumUpgradeCard(
                    onUpgradeClick = { onEvent(SettingsUiEvent.SubscriptionClicked) }
                )
            }

            /* ───── Account ───── */
            item { SettingsSectionHeader("ACCOUNT") }

            item {
                SettingsNavigationItem(
                    icon = Icons.Outlined.Person,
                    title = "Profile Settings",
                    subtitle = "Manage your identity and bio",
                    onClick = { onEvent(SettingsUiEvent.ProfileClicked) }
                )
            }

            item {
                SettingsNavigationItem(
                    icon = Icons.Outlined.CreditCard,
                    title = "Subscription Plan",
                    subtitle = "Free Tier active",
                    trailingText = "UPGRADE",
                    onClick = { onEvent(SettingsUiEvent.SubscriptionClicked) }
                )
            }

            /* ───── Preferences ───── */
            item { SettingsSectionHeader("PREFERENCES") }

            item {
                SettingsNavigationItem(
                    icon = Icons.Outlined.AttachMoney,
                    title = "Default Currency",
                    trailingText = "${uiState.defaultCurrency.code} (${uiState.defaultCurrency.symbol})",
                    onClick = {onEvent(SettingsUiEvent.CurrencyClicked)}
                )
            }

            item {
                SettingsToggleItem(
                    icon = Icons.Outlined.Notifications,
                    title = "Push Notifications",
                    checked = uiState.notificationsEnabled,
                    onCheckedChange = {onEvent(SettingsUiEvent.NotificationsToggled(it))}
                )
            }

            item {
                SettingsToggleItem(
                    icon = Icons.Outlined.DarkMode,
                    title = "Dark Appearance",
                    checked = uiState.darkModeEnabled,
                    onCheckedChange = {onEvent(SettingsUiEvent.DarkModeToggled(it))}
                )
            }

            /* ───── Security ───── */
            item { SettingsSectionHeader("SECURITY") }

            item {
                SettingsNavigationItem(
                    icon = Icons.Outlined.Lock,
                    title = "Passcode & Face ID",
                    subtitle = "Protect app access",
                    onClick = { onEvent(SettingsUiEvent.PasscodeClicked) }
                )
            }

            /* ───── Support ───── */
            item { SettingsSectionHeader("SUPPORT") }

            item {
                SettingsNavigationItem(
                    icon = Icons.Outlined.HelpOutline,
                    title = "Help & Feedback",
                    onClick = { onEvent(SettingsUiEvent.SupportClicked) }
                )
            }

            /* ───── Logout ───── */
            item {
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = { onEvent(SettingsUiEvent.LogoutClicked) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Logout,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Logout")
                }
            }

            /* ───── Version ───── */
            item {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Expense Tracker v${APP_VERSION}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        if (uiState.showLogoutDialog) {
            LogoutConfirmationDialog(
                onConfirm = {
                    onEvent(SettingsUiEvent.ConfirmLogout)
                },
                onDismiss = {
                    onEvent(SettingsUiEvent.DismissLogoutDialog)
                }
            )
        }

        if(uiState.showCurrencySheet){
            CurrencyBottomSheet("USD", onCurrencySelected = {
                onEvent(SettingsUiEvent.ConfirmCurrencyChange(it))
            }, onDismiss = {
                onEvent(SettingsUiEvent.DismissCurrencySheet)
            })
        }

    }
}

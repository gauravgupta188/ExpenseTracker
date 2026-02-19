package com.app.expensetracker.feature.settings.ui.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import com.app.expensetracker.feature.settings.domain.model.CurrencyProvider.supportedCurrencies

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyBottomSheet(
    currentCurrency: String,
    onCurrencySelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        LazyColumn {
            items(supportedCurrencies) { currency ->
                CurrencyRow(
                    item = currency,
                    isSelected = currency.code == currentCurrency,
                    onClick = {
                        onCurrencySelected(currency.code)
                        onDismiss()
                    }
                )
            }
        }
    }
}

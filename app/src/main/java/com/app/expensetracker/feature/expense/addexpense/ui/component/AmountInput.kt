package com.app.expensetracker.feature.expense.addexpense.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.settings.domain.model.CurrencyItem

@Composable
fun AmountInput(
    value: String,
    currencyItem: CurrencyItem,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "AMOUNT",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = currencyItem.symbol,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            BasicTextField(
                value = value,
                onValueChange = { input ->
                    // Allow only digits and dot
                    if (input.matches(Regex("^\\d*(\\.\\d{0,2})?$"))) {
                        onValueChange(input)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                textStyle = MaterialTheme.typography.displaySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                singleLine = true,
                modifier = Modifier.widthIn(min = 80.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(0.7f), thickness = 2.dp, color = MaterialTheme.colorScheme.primary
        )
    }
}

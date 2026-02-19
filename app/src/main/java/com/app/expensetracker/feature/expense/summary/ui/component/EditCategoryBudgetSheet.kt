package com.app.expensetracker.feature.expense.summary.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.app.expensetracker.core.components.AppButton
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCategoryBudgetSheet(
    category: ExpenseCategory,
    currentBudget: Double?,
    onSave: (Double) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {

        var value by remember { mutableStateOf(currentBudget?.toString() ?: "") }

        Column(  modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text("Set Budget for ${category.value}", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = value,
                onValueChange = { value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Budget Amount") }
            )

            Spacer(modifier = Modifier.height(24.dp))

            AppButton(
                onClick = { onSave(value.toDouble()) },
                text = "Save Budget",
            )
        }
    }
}

package com.app.expensetracker.core.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePickerDialog(
    initialDate: LocalDateTime,
    onDateSelected: (LocalDateTime) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis =
            initialDate
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val millis = datePickerState.selectedDateMillis
                        ?: return@TextButton

                    val selectedDate = Instant.ofEpochMilli(millis)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()

                    onDateSelected(selectedDate)
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

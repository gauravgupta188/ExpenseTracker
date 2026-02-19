package com.app.expensetracker.core.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable (() -> Unit))? = null

) {


    Spacer(modifier = Modifier.height(6.dp))
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            disabledContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f),
            contentColor = MaterialTheme.colorScheme.onSecondary,
            disabledContentColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.6f)
        )


    ) {
        Row {
            leadingIcon?.let { it()
            Spacer(modifier.width(8.dp))
            }

            Text(text, style = MaterialTheme.typography.labelLarge, color = Color.White)
        }
    }


}

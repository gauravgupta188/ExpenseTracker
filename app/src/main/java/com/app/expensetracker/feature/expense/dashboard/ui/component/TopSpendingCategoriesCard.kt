package com.app.expensetracker.feature.expense.dashboard.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.expensetracker.core.components.AppCard
import com.app.expensetracker.feature.expense.summary.model.CategorySummaryUiModel
import kotlin.collections.take

@Composable
fun TopSpendingCategoriesCard(
    categories: List<CategorySummaryUiModel>
) {
    AppCard{
        Column() {

            Text(
                text = "Top Spending Categories",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(12.dp))

            categories.take(3).forEach {
                CategorySpendRow(it)
            }
        }
    }
}

package com.app.expensetracker.feature.expense.dashboard.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.expense.component.ExpenseSectionHeader
import com.app.expensetracker.feature.expense.domain.model.MonthHighlightsUi

@Composable
fun MonthHighlightsSection(
    highlights: MonthHighlightsUi
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ExpenseSectionHeader(
            onViewAllClick = {  },
            isShowViewAll = false,
            title = "Month Highlights"
        )
        TopSpendingCategoriesCard(highlights.topCategories)
        BudgetOverviewCard(highlights.budgetInsights)
        MonthComparisonCard(highlights.comparison)
    }
}

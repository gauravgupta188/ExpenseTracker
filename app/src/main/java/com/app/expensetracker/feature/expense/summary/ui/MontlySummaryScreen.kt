package com.app.expensetracker.feature.expense.summary.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.expense.summary.state.MonthlySummaryUiState
import com.app.expensetracker.feature.expense.summary.ui.component.CategoryBreakdownHeader
import com.app.expensetracker.feature.expense.summary.ui.component.CategoryBreakdownItem
import com.app.expensetracker.feature.expense.summary.ui.component.SpendingInsightCard
import com.app.expensetracker.feature.expense.summary.ui.component.SummaryCards
import com.app.expensetracker.feature.expense.summary.ui.component.SummaryHeader
import com.app.expensetracker.feature.expense.summary.ui.component.SummaryStatsSection
import com.app.expensetracker.ui.theme.BrandBlack

@Composable
fun MonthlySummaryScreen(
    state: MonthlySummaryUiState,
    onBackClick: () -> Unit,
    onMonthSelectorClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    onViewAllCategoriesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxSize().background(Color.Black)
            .navigationBarsPadding().statusBarsPadding()
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {

            /* ---------- HEADER ---------- */
            item {
                SummaryHeader(
                    selectedMonth = state.selectedMonth,
                    onBackClick = onBackClick,
                    onMonthClick = onMonthSelectorClick,
                    subtitle = "Your spending performance is 8% lower than last month",

                )
            }

            /* ---------- STATS ---------- */
            item {
                SummaryStatsSection(
                    totalSpent = state.totalSpent,
                    remainingAmount = state.remainingAmount,
                    budgetAmount = state.budgetAmount,
                    spendingChangePercent = state.spendingChangePercent,
                    isSpendingDown = state.isSpendingDown
                )
            }

            /* ---------- INSIGHT ---------- */
            if (state.insightMessage.isNotBlank()) {
                item {
                    SpendingInsightCard(
                        message = state.insightMessage
                    )
                }
            }

            /* ---------- CATEGORY HEADER ---------- */
            item {
                CategoryBreakdownHeader(
                    onViewAllClick = onViewAllCategoriesClick
                )
            }

            /* ---------- CATEGORY LIST ---------- */
            items(
                items = state.categories,
                key = { it.category.name }
            ) { category ->
                CategoryBreakdownItem(
                    model = category
                )
            }

            /* ---------- LOADING ---------- */
            if (state.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            /* ---------- ERROR ---------- */
            state.errorMessage?.let { error ->
                item {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)

                    )
                }
            }
        }
    }
}

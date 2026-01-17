package com.app.expensetracker.feature.expense.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiEvent
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiState
import com.app.expensetracker.feature.expense.dashboard.ui.component.AddExpenseFab
import com.app.expensetracker.feature.expense.dashboard.ui.component.DashboardExpenseSection
import com.app.expensetracker.feature.expense.dashboard.ui.component.DashboardTopSection
import com.app.expensetracker.ui.theme.BrandBlack

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    state: ExpenseUiState,
    onEvent: (ExpenseUiEvent) -> Unit,
    onAddExpenseClick: () -> Unit,
    onViewAllClick: () -> Unit
) {
    Box(modifier = modifier
        .fillMaxSize()
        .background(BrandBlack)
        .navigationBarsPadding()) {

        Column(modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()) {

            DashboardTopSection(
                modifier = Modifier.weight(0.4f),
                uiState = state
            )

            DashboardExpenseSection(
                modifier = Modifier.weight(0.6f),
                state = state,
                onViewAllClick = onViewAllClick
            )
        }

        AddExpenseFab(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            onAddExpenseClick = onAddExpenseClick

        )
    }
}












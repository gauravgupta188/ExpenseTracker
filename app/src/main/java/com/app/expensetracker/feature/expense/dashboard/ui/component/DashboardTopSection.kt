package com.app.expensetracker.feature.expense.dashboard.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.expense.dashboard.state.ExpenseUiState
import com.app.expensetracker.feature.expense.domain.model.YearMonthUiModel


@Composable
fun DashboardTopSection(
    modifier: Modifier = Modifier,
    uiState: ExpenseUiState,
    onMonthSelected: (YearMonthUiModel) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        val listState = rememberLazyListState()

        LaunchedEffect(uiState.selectedMonth) {
            val index = uiState.months.indexOf(uiState.selectedMonth)
            if (index >= 0) {
                listState.animateScrollToItem(index)
            }
        }



        Spacer(Modifier.height(16.dp))

        DashboardHeader()

        Spacer(Modifier.height(16.dp))

        DashboardBudgetCard(spend = uiState.totalAmount,remaining = 25000.00)

      /*  MonthSelector(
            months = uiState.months,
            selectedMonth = uiState.selectedMonth,
            listState = listState,
            onMonthSelected = onMonthSelected
        )*/

        Spacer(Modifier.height(32.dp))

        /*MonthlyTotal(
            amount = "$ ${uiState.totalAmount}"
        )*/
    }
}


@Composable
private fun MonthlyTotal(
    amount: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "TOTAL THIS MONTH",
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = amount,
            style = MaterialTheme.typography.displaySmall,
            color = Color(0xFFFF9800)
        )
    }
}

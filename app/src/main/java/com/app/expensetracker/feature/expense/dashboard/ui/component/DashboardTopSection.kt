package com.app.expensetracker.feature.expense.dashboard.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.app.expensetracker.ui.theme.BrandBlack


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
           // .padding(horizontal = 20.dp)
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

        Box() {
            // Top black section
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(BrandBlack)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(Color.White)
                )
            }
        }
    }
}

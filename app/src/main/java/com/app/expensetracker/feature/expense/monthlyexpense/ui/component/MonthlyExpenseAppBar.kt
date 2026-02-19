package com.app.expensetracker.feature.expense.monthlyexpense.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.expensetracker.feature.settings.domain.model.CurrencyItem
import com.app.expensetracker.ui.theme.BrandBlack


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthlyExpenseAppBar(
    subtitle: String,
    expenseCount: String,
    currencyItem: CurrencyItem,
    total: String,
    onBackClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val collapsedFraction = scrollBehavior.state.collapsedFraction
    LargeTopAppBar(
        title = {
        Column {
            Text(
                text = "Expenses", style = MaterialTheme.typography.headlineSmall, maxLines = 1
            )


                AnimatedVisibility(
                    visible = collapsedFraction < 0.6f, enter = fadeIn(), exit = fadeOut()
                ) {
                    Row(modifier = Modifier.padding(end = 16.dp)) {
                        Column {

                            Text(
                                text = "TOTAL SPEND",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                maxLines = 1
                            )
                            Text(
                                text = "${currencyItem.symbol}${total}",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.White,
                                maxLines = 1
                            )

                            /* ───────── Expense Count Chip ───────── */

                            //  Spacer(modifier = Modifier.height(8.dp))
                        }
                        Spacer(modifier = Modifier.weight(1f))

                        Row(
                            modifier = Modifier
                                .background(
                                    color = Color.White.copy(alpha = 0.12f),
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ReceiptLong,
                                contentDescription = null,
                                tint = Color(0xFFFF9800),
                                modifier = Modifier.size(18.dp)
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = "$expenseCount expenses",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                        }
                    }
                }
        }
    }, navigationIcon = {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back"
            )
        }
    }, colors = TopAppBarDefaults.largeTopAppBarColors(
        containerColor = BrandBlack,
        scrolledContainerColor = BrandBlack,
        titleContentColor = Color.White,
        navigationIconContentColor = Color.White
    ), scrollBehavior = scrollBehavior
    )
}

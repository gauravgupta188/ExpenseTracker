package com.app.expensetracker.feature.expense.dashboard.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    title : String,
    subtitle : String = "",
    monthSelectorClick : () -> Unit,
    settlingClick : () -> Unit
) {
    val collapsedFraction = scrollBehavior.state.collapsedFraction
    LargeTopAppBar(
        title = {
            Row(modifier = Modifier.padding(16.dp)) {

                Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = CircleShape
                    )
                    .padding(0.dp),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.AccountBalanceWallet,
                    contentDescription = null,
                    tint = Color.White
                )
            }
                Spacer(Modifier.width(8.dp))
             //   Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(title, style = MaterialTheme.typography.headlineSmall, maxLines = 1)
                        Text(
                            "Monthly Overview",
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 1
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                AnimatedVisibility(
                    visible = collapsedFraction < 0.6f,
                    enter = fadeIn(),
                    exit = fadeOut(),

                ) {
                    OutlinedButton(
                        contentPadding = PaddingValues(
                            top = 4.dp,
                            bottom = 4.dp,
                            start = 12.dp,
                            end = 12.dp
                        ),
                        onClick = { monthSelectorClick() }, border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Text(
                            text = subtitle,
                            color = Color.White
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                }
         //   }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.Black,
            scrolledContainerColor = Color.Black,
            titleContentColor = Color.White
        ),
        actions = {


            IconButton(onClick = { settlingClick() }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = Color.White
                )
            }


        },
        scrollBehavior = scrollBehavior
    )
}

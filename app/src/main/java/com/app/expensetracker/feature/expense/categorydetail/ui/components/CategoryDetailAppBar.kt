package com.app.expensetracker.feature.expense.categorydetail.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.app.expensetracker.feature.expense.categorydetail.state.CategoryDetailUiEvent
import com.app.expensetracker.feature.expense.domain.model.ExpenseCategory
import com.app.expensetracker.ui.theme.BrandBlack


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailAppBar(
    category: ExpenseCategory,
    onBackClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
  //  val collapsedFraction = scrollBehavior.state.collapsedFraction
    LargeTopAppBar(
        title = {
            CategoryDetailHeader(
                category = category,
            )
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

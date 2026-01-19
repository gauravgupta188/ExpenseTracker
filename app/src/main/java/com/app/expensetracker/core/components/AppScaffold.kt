package com.app.expensetracker.core.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.app.expensetracker.ui.theme.BrandBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    containerColor: Color = BrandBlack,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),

        // 🔴 Critical for edge-to-edge + status bar coloring
        contentWindowInsets = WindowInsets(0),

        // 🔴 This prevents white status bar
        containerColor = containerColor,

        topBar = {
            topBar?.invoke()
        },

        bottomBar = {
            bottomBar?.invoke()
        },
/*
        floatingActionButton = {
            floatingActionButton?.invoke()
        },*/


        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { padding ->
        content(padding)
    }
}

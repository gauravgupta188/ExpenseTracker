package com.app.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.app.expensetracker.core.navigation.AppNavGraph
import com.app.expensetracker.core.ui.theme.ExpenseTrackerTheme
import com.app.expensetracker.core.viewmodel.AppStartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var isAppReady = false
    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        splashScreen.setKeepOnScreenCondition {
            !isAppReady
        }

        lifecycleScope.launch {
            delay(1200) // simulate loading
            isAppReady = true
        }

        setContent {
          InitApp()
        }
    }
}

@Composable
fun InitApp() {
    // Later this can come from DataStore / Settings
    val isDarkTheme = isSystemInDarkTheme()
    val appStartViewModel: AppStartViewModel = hiltViewModel()
    val isFirebaseLoggedIn = appStartViewModel.isLoggedIn
        .collectAsState().value


    ExpenseTrackerTheme(
        darkTheme = isDarkTheme
    ) {
        val navController = rememberNavController()

            AppNavGraph(
                navController = navController,
                isLoggedIn = isFirebaseLoggedIn // replace later with real state
            )

    }
}

package com.example.speedometer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.speedometer.screen.DashboardScreen
import com.example.speedometer.ui.theme.SpeedometerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val dashboardViewModel: DashboardViewModel by viewModels()
            val dashboardMode by dashboardViewModel.mode.collectAsState()

            Log.d("MainActivity", "setContent, mode = $dashboardMode")
            SpeedometerTheme(darkTheme = dashboardMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    DashboardScreen(viewModel = dashboardViewModel, dashboardMode)
                }
            }
        }
    }
}

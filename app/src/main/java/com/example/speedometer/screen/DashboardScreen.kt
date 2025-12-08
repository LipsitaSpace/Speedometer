package com.example.speedometer.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.speedometer.DashboardViewModel
import com.example.speedometer.screen.components.Speedometer
import com.example.speedometer.screen.components.TimeAndDistance
import com.example.speedometer.screen.components.TripDataScreen

@Composable
fun DashboardScreen(viewModel: DashboardViewModel, dashboardMode: Boolean) {
    val TAG = "DashboardScreen"
    val context = androidx.compose.ui.platform.LocalContext.current
    LaunchedEffect(Unit) {
        Log.d(TAG, "LaunchedEffect")
        viewModel.bindService(context)
    }
    DisposableEffect(Unit) {
        onDispose {
            Log.d(TAG, "onDispose")
            viewModel.unbindService(context)
        }
    }

    val dashboardSpeed: Float by viewModel.speed.collectAsState()
    val dashboardDistance: Float by viewModel.distance.collectAsState()
    val dashboardTime: String by viewModel.time.collectAsState()
    val dashboardUnit: String by viewModel.unit.collectAsState()
    val dashboardTotalDistance: Float by viewModel.totalDistance.collectAsState()
    val dashboardTotalTime: Float by viewModel.totalTime.collectAsState()
    val avgSpeed = dashboardTotalDistance / dashboardTotalTime

    Log.d(
        TAG,
        "$dashboardSpeed + $dashboardDistance + $dashboardTime + $dashboardMode + $dashboardUnit + $dashboardTotalDistance + $dashboardTotalTime"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimeAndDistance(dashboardDistance, dashboardTime, avgSpeed)
        Speedometer(dashboardSpeed, dashboardUnit, dashboardMode)
        TripDataScreen(dashboardMode)
    }
}
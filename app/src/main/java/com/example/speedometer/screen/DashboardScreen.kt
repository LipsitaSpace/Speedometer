package com.example.speedometer.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.speedometer.DashboardViewModel
import com.example.speedometer.screen.components.Speedometer
import com.example.speedometer.screen.components.TimeAndDistance
import com.example.speedometer.screen.components.TripDataScreen

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = viewModel()) {
    val TAG = "Lipsita DashboardScreen"
    val context = androidx.compose.ui.platform.LocalContext.current

    val data by viewModel.data.collectAsState()

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

    val speed = data.speed
    val unit = data.unit
    val mode = data.isDayMode

    Log.d(TAG, "$speed + $unit + $mode")

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimeAndDistance()
        Speedometer(speed, unit, mode)
        TripDataScreen(mode)
    }
}
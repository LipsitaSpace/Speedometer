package com.example.speedometer.screen

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
    val context = androidx.compose.ui.platform.LocalContext.current
    val bundle by viewModel.data.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.bindService(context)
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.unbindService(context)
        }
    }

    val speed = bundle?.getInt("speed", 0) ?: 0
    val unit = bundle?.getString("unit", "km/h") ?:"km/h"
    val mode = bundle?.getString("mode") == "day"

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimeAndDistance()
        Speedometer(speed, unit, mode)
        TripDataScreen(mode)
    }
}
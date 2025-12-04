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
    val bundle by viewModel.data.collectAsState()
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

    //val speed = bundle?.getFloat("speed", 0f) ?: 0f
    val speed: Float by viewModel.speed.collectAsState()
    val mydistance : Float by viewModel.distance.collectAsState()
    val myTime : String by viewModel.time.collectAsState()
    val unit = bundle?.getString("unit", "km/h") ?:"km/h"
    val mode = bundle?.getString("mode") == "day"

    Log.d(TAG, "$speed + $unit + $mode")

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimeAndDistance(mydistance,myTime)
        Speedometer(speed, unit, mode)
        TripDataScreen(mode)
    }
}
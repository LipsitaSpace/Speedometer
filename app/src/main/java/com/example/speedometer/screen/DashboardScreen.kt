package com.example.speedometer.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.speedometer.screen.components.Speedometer
import com.example.speedometer.screen.components.TimeAndDistance
import com.example.speedometer.screen.components.TripDataScreen

@Preview
@Composable
fun DashboardScreen() {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TimeAndDistance()
        Speedometer(100, "km/h", true)
        TripDataScreen(true)
    }
}
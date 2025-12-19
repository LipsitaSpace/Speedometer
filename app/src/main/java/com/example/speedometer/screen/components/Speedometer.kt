package com.example.speedometer.screen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedometer.ui.theme.DarkBlack
import com.example.speedometer.ui.theme.DarkBlue
import com.example.speedometer.ui.theme.GlowingBlue
import com.example.speedometer.ui.theme.LightBlue
import com.example.speedometer.ui.theme.LightWhite

@Composable
fun Speedometer(speed: Float, unit: String, mode: Boolean) {
    val formattedSpeed = String.format("%.1f", speed)
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.testTag("SpeedometerRoot")
    ) {
        Canvas(
            modifier = Modifier
                .size(200.dp)
        ) {
            val sweepAngle = (speed / 240f) * 270f
            drawArc(
                color = if(mode) GlowingBlue else DarkBlue,
                startAngle = 135f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 20f, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                formattedSpeed,
                fontSize = 48.sp,
                color = if (mode) LightWhite else DarkBlack,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag("SpeedText")
            )
            Text(
                unit,
                fontSize = 26.sp,
                color = if (mode) LightBlue else DarkBlue,
                modifier = Modifier.testTag("UnitText")
            )
        }
    }
}

@Preview
@Composable
fun SpeedometerPreview() {
    Speedometer(10f,"km/h", true)
}
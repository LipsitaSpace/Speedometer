package com.example.speedometer.screen.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Preview
@Composable
fun TimeAndDistance(
    avgSpeed: Int = 0,
    distance: Int = 0
) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    var currentTime by remember { mutableStateOf("00:00:00") }

    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(1000)
            currentTime = LocalTime.now().format(timeFormatter)
        }
    }

    // Modern Digital Cluster Colors
    val mainText = Color(0xFFEFEFEF)
    val subText = Color(0xFF9EA3A8)
    val accent = Color(0xFF4DA3FF)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // TOP BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            // LEFT — Distance
            Column(modifier = Modifier.padding(top = 50.dp)) {
                Text("DISTANCE", color = subText, fontSize = 12.sp)
                Text(
                    text = String.format("%02d", distance),
                    color = accent,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // CENTER — Time
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = currentTime,
                    color = mainText,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .height(2.dp)
                        .width(80.dp)
                        .background(accent)
                )
            }

            // RIGHT — Avg Speed
            Column(modifier = Modifier.padding(top = 50.dp)) {
                Text("AVG SPEED", color = subText, fontSize = 12.sp)
                Text(
                    text = String.format("%02d", avgSpeed),
                    color = accent,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

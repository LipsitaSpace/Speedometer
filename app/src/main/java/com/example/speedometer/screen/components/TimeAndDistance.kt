package com.example.speedometer.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.speedometer.ui.theme.DarkBlack
import com.example.speedometer.ui.theme.DarkBlue
import com.example.speedometer.ui.theme.LightBlue
import com.example.speedometer.ui.theme.LightWhite


@Composable
fun TimeAndDistance(
    distance: Float, time: String, avgSpeed: Float, mode: Boolean
) {
    val formattedDistance = String.format("%.2f", distance)
    val formattedAvgSpeed = String.format("%.2f", avgSpeed)

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.padding(top = 25.dp)) {
                Text("DISTANCE", color = if(mode) LightWhite else DarkBlack, fontSize = 15.sp)
                Text(
                    text = formattedDistance,
                    color = if(mode) LightBlue else DarkBlue,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = time,
                    color = if(mode) LightBlue else DarkBlue,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Box(
                    modifier = Modifier
                        .height(2.dp)
                        .width(80.dp)
                        .background(if(mode) LightWhite else DarkBlack)
                )
            }

            Column(modifier = Modifier.padding(top = 25.dp)) {
                Text("AVG SPEED", color = if(mode) LightWhite else DarkBlack, fontSize = 15.sp)
                Text(
                    text = formattedAvgSpeed,
                    color = if(mode) LightBlue else DarkBlue,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun TimeAndDistancePreview(){
    TimeAndDistance(200f, "10:20:43", 40f,true)
}
package com.example.speedometer.screen.components


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.speedometer.ui.theme.DarkBlack
import com.example.speedometer.ui.theme.DarkBlue
import com.example.speedometer.ui.theme.LightBlue
import com.example.speedometer.ui.theme.LightWhite


@Composable
fun TripDataScreen(modeChange: Boolean) {
    var startLoc by remember { mutableStateOf("Bangalore") }
    var endLoc by remember { mutableStateOf("Chennai") }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(15.dp, 0.dp, 0.dp, 0.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            SimpleInfo("Start Location", "Bangalore", modeChange)
            SimpleInfo("Start Time", "12:30 PM", modeChange)
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            myButton()
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.End
        ) {
            SimpleInfo("Destination", "Chennai", modeChange)
            SimpleInfo("End TIme    ", "4:30 PM", modeChange)
        }

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SimpleInfo(name: String, value: String, changeMode: Boolean) {
    Column {
        Text(
            text = name,
            color = if (changeMode) LightWhite else DarkBlack,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .border(1.dp, Color.Gray, RectangleShape)
                .padding(8.dp)
        ) {
            Text(
                text = value,
                color = if (changeMode) LightBlue else DarkBlue,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun myButton() {

        var buttonText by remember { mutableStateOf("START") }
        var buttonColor by remember { mutableStateOf(Color.Green) }

        Box(
            modifier = Modifier
                .size(130.dp)
                .background(buttonColor, shape = CircleShape)
                .combinedClickable(
                    onClick = {
                        if (buttonText == "START") {
                            buttonText = "STOP"
                            buttonColor = Color.Red
                        } else {
                            buttonText = "START"
                            buttonColor = Color.Green
                        }

                    },
                    onLongClick = {
                        buttonText = "RESET"
                        buttonColor = Color.Blue
                    }
                ))
        {
            Text(
                text = buttonText,
                color = if (buttonText == "RESET") Color.White else Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
@Preview
@Composable
fun PreviewMyScreen(){
    TripDataScreen(true)
}
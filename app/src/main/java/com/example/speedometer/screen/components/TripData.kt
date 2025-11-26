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
import androidx.compose.material3.Surface
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

@Preview
@Composable
fun TripDataScreen(){
    var startLoc by remember { mutableStateOf("Bangalore") }
    var endLoc by remember { mutableStateOf("Chennai")}
    var surfaceColor by remember{mutableStateOf(Color.Black)}

    Surface (
        modifier = Modifier
            .fillMaxSize(),
        color = surfaceColor
    ){
        Row (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column( modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(15.dp,0.dp,0.dp,0.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                SimpleInfo("Start Location","Bangalore",surfaceColor)
                SimpleInfo("Start Time", "12:30 PM",surfaceColor)
            }

            Column (modifier = Modifier
                .align(Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)


            ){
                myButton()
            }

            Column ( modifier = Modifier
                .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.End
            ){
                SimpleInfo("Destination","Chennai",surfaceColor)
                SimpleInfo("End TIme    ","4:30 PM",surfaceColor)
            }

        }
    }


}

@Composable
fun SimpleInfo(name : String, value : String, colorName : Color){
    Column {
        Text(text = name, color = if(colorName == Color.Black) LightWhite else DarkBlack, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
        Box(
            modifier = Modifier
                .border(1.dp, Color.Gray,RectangleShape)
                .padding(8.dp)){
                    Text(text = value, color = if(colorName == Color.Black) LightBlue else DarkBlue, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun myButton(){

    var buttonText by remember { mutableStateOf("START") }
    var buttonColor by remember { mutableStateOf(Color.Green) }

    Box(modifier = Modifier
        .size(130.dp)
        .background(buttonColor, shape = CircleShape)
        .combinedClickable(
            onClick = {
                if(buttonText == "START"){
                    buttonText = "STOP"
                    buttonColor = Color.Red
                }
                else {
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
            Text(text = buttonText, color = if(buttonText == "RESET")Color.White else Color.Black, modifier = Modifier.align(Alignment.Center))
        }
    }
package com.example.speedometer.screen.components

import android.util.Log
import android.widget.TextView
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.speedometer.DashboardViewModel
import com.example.speedometer.data.TripDataRepo
import com.example.speedometer.ui.theme.DarkBlack
import com.example.speedometer.ui.theme.DarkBlue
import com.example.speedometer.ui.theme.LightBlue
import com.example.speedometer.ui.theme.LightWhite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.String


const val TAG = "TripDataScreen"

@Composable
fun TripDataScreen(modeChange: Boolean, time : String) {
    val scope = CoroutineScope((Dispatchers.IO))
    val context = LocalContext.current
    val mTripDataRepo = TripDataRepo(context)
    val mDashBoardVM = viewModel<DashboardViewModel>()
    var startLoc by remember { mutableStateOf("start location") }
    var endLoc by remember { mutableStateOf("destination") }
    var startTime: String  by remember { mutableStateOf("00:00:00")}
    var endTime: String  by remember { mutableStateOf("00:00:00") }
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
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            SimpleInfo("Start Location", startLoc, modeChange)
            SimpleInfo("Start Time", startTime, modeChange)
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            MyButton(
                onStart = {
                    mDashBoardVM.pickNewTrip()
                    startLoc = mDashBoardVM.startLocation.value
                    Log.d(TAG,"Time is $time")
                    startTime = time
                    endLoc = "destination"
                    endTime = "00:00:00"
                },
                onStop = {
                    mDashBoardVM.pickNewTrip()
                    endLoc = if(startLoc!=mDashBoardVM.destination.value) mDashBoardVM.destination.value else TODO()
                    endTime = time
                         },
                reset = {
                    val snapStartLoc = startLoc
                    val snapStartTime = startTime
                    val snapEndLocation = endLoc
                    val snapEndTime = endTime
                    scope.launch {
                        mTripDataRepo.saveTripData(snapStartLoc,snapStartTime,snapEndLocation,snapEndTime)
                    }
                    startLoc = "start location"
                    startTime = "00:00:00"
                    endLoc = "destination "
                    endTime = "00:00:00"
                }
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalAlignment = Alignment.End
        ) {
            SimpleInfo("Destination", endLoc, modeChange)
           SimpleInfo("End TIme    ", endTime, modeChange)
        }

    }
}


@Composable
fun SimpleInfo(name: String, value: String, changeMode: Boolean) {
    Log.d(TAG,"value is $value")
    Column {
        Text(
            text = name,
            color = if (changeMode) LightWhite else DarkBlack,
            fontWeight = FontWeight.Medium
        )
        Box(
            modifier = Modifier
                .border(1.dp, Color.Gray, RectangleShape)
                .padding(2.dp)
        ) {
            Text(
                text = value,
                color = if (changeMode) LightBlue else DarkBlue,
                fontWeight = FontWeight.Medium
            )
        }

    }
}

    @Composable
    fun MyButton(
        onStart: ()->Unit,
        onStop:()-> Unit,
        reset : () -> Unit
    ) {
        var buttonText by remember { mutableStateOf("START") }
        var buttonColor by remember { mutableStateOf(Color.Green) }
        val context = LocalContext.current

        fun showCustomToast(message: String) {
            val toast = Toast(context.applicationContext)

            val tv = TextView(context).apply {
                text = message
                setTextColor(android.graphics.Color.WHITE)
                setPadding(24, 12, 24, 12)
                setBackgroundColor(android.graphics.Color.BLACK)
            }

            toast.view = tv
            toast.duration = Toast.LENGTH_SHORT
            toast.show()
        }

        Box(
            modifier = Modifier
                .size(130.dp)
                .background(buttonColor, shape = CircleShape)
                .combinedClickable(
                    onClick = {
                        if (buttonText == "START") {
                            onStart()
                            buttonText = "STOP"
                            buttonColor = Color.Red
                        } else {
                            onStop()
                            buttonText = "START"
                            buttonColor = Color.Green
                        }

                    },
                    onLongClick = {
                        if (buttonText == "STOP") {
                            showCustomToast("Trip not ended")
                            return@combinedClickable
                        }

                        showCustomToast("TRIP DATA RESET")

                        reset()
                        buttonText = "START"
                        buttonColor = Color.Green
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
//@Preview
//@Composable
//fun PreviewMyScreen(){
//    TripDataScreen(true)
//}
package com.example.speedometer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simulatorservice.ISimulatorInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SimulatorData(
    val speed: Float = 0f,
    val distance: Float = 0f,
    val systemTime: Long = 0L,
    val ignitionState: Boolean = false,
    val isDayMode: Boolean = false,
    val unit: String = "km/h"
)
class DashboardViewModel : ViewModel() {
    private var iService : ISimulatorInterface? = null
    private val _data = MutableStateFlow(SimulatorData())
    val data: StateFlow<SimulatorData> = _data
    val TAG = "Lipsita DashboardViewModel"

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            Log.d(TAG, "onServiceConnected")
            iService = ISimulatorInterface.Stub.asInterface(service)
            fetchData()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected")
            iService = null
        }
    }
    fun bindService(context: Context) {
        Log.d(TAG, "bindService")
        val intent = Intent().apply {
            component = ComponentName(
                "com.example.simulatorservice",
                "com.example.simulatorservice.SimulatorService"
            )
        }
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    fun unbindService(context: Context) {
        Log.d(TAG, "unbindService")
        context.unbindService(connection)
    }

    fun fetchData() {
        iService?.let { service ->
            viewModelScope.launch(Dispatchers.IO) {
                val newData = SimulatorData(
                    speed = service.getSpeed(),
                    distance = service.getDistance(),
                    systemTime = service.getSystemTime(),
                    ignitionState = service.isIgnitionOn(),
                    isDayMode = service.isDayMode(),
                    unit = service.getUnit()
                )
                _data.value = newData
                Log.d(TAG, "Fetched data: ${newData.speed}, ${newData.unit}")
            }
        }
    }
}
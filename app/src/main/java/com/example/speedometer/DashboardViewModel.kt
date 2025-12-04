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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private var iService : ISimulatorInterface? = null
    private val _speed = MutableStateFlow(0f)
    val speed: StateFlow<Float> = _speed

    private val _distance = MutableStateFlow(0f)
    val distance: StateFlow<Float> = _distance

    private val _time = MutableStateFlow("")
    val time: MutableStateFlow<String> = _time

    private val _data = MutableStateFlow<Bundle?>(null)
    val data: StateFlow<Bundle?> = _data
    val TAG = "Lipsita DashboardViewModel"

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            Log.d(TAG, "onServiceConnected")
            iService = ISimulatorInterface.Stub.asInterface(service)
            fetchData()
            fetchSpeed()
            fetchDistance()
            fetchTime()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected")
            iService = null
        }
    }

    fun bindService(context: Context) {
        Log.d(TAG, "bindService")
        val intent = Intent("com.example.simulatorservice.SimulatorService").apply {
            `package` = "com.example.simulatorservice"
        }
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    fun unbindService(context: Context) {
        Log.d(TAG, "unbindService")
        context.unbindService(connection)
    }
    fun fetchData() {
        Log.d(TAG,"fetch data")
        iService?.let { service ->
            viewModelScope.launch(Dispatchers.IO) {
                val bundle = Bundle()
                service.getData(bundle)
                _data.value = bundle
            }
        }
    }
    fun fetchSpeed(){
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                val value = try {
                    iService?.getSpeed() ?: 0f
                } catch (e: Exception) {
                    0f
                }
                _speed.value = value
                Log.d("prudviapp","speed  is $value")
                delay(200) // poll every 200ms
            }
        }
    }

    fun fetchDistance(){
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                val value = try {
                    iService?.getDistance() ?: 0f
                } catch (e: Exception) {
                    0f
                }
                _distance.value = value
                Log.d("prudviapp","distance  is $value")
                delay(200) // poll every 200ms
            }
        }
    }

    fun fetchTime(){
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                val value = try {
                    iService?.getTime() ?: ""
                } catch (e: Exception) {
                    0f
                }
                _time.value = value as String
                Log.d("prudviapp","speed  is $value")
                delay(200) // poll every 200ms
            }
        }
    }
}
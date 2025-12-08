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

    private val cities = listOf(
        "Bangalore", "Chennai", "Hyderabad", "Mumbai", "Delhi",
        "Pune", "Kolkata", "Ahmedabad", "Jaipur", "Lucknow"
    )
    private val _startLocation = MutableStateFlow("")
    val startLocation: StateFlow<String> = _startLocation
    private val _destination = MutableStateFlow("")
    val destination: StateFlow<String> = _destination
    private var iService: ISimulatorInterface? = null
    private val _speed = MutableStateFlow(0f)
    val speed: StateFlow<Float> = _speed

    private val _distance = MutableStateFlow(0f)
    val distance: StateFlow<Float> = _distance

    private val _time = MutableStateFlow("")
    val time: MutableStateFlow<String> = _time
    private val _mode = MutableStateFlow(true)
    val mode: MutableStateFlow<Boolean> = _mode
    private val _unit = MutableStateFlow("km/h")
    val unit: StateFlow<String> = _unit
    private val _totalDistance = MutableStateFlow(0f)
    val totalDistance: StateFlow<Float> = _totalDistance
    private val _totalTime = MutableStateFlow(0f)
    val totalTime: StateFlow<Float> = _totalTime

    val TAG = "DashboardViewModel"

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            Log.d(TAG, "onServiceConnected")
            iService = ISimulatorInterface.Stub.asInterface(service)
            fetchSpeed()
            fetchDistance()
            fetchTime()
            fetchMode()
            fetchUnit()
            fetchTotalTime()
            fetchTotalDistance()
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

    fun fetchSpeed() {
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                val value = try {
                    iService?.getSpeed() ?: 0f
                } catch (e: Exception) {
                    0f
                }
                _speed.value = value
                Log.d(TAG, "speed  is $value")
                delay(200)
            }
        }
    }

    fun fetchDistance() {
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                val value = try {
                    iService?.getDistance() ?: 0f
                } catch (e: Exception) {
                    0f
                }
                _distance.value = value
                Log.d(TAG, "distance  is $value")
                delay(200)
            }
        }
    }

    fun fetchTime() {
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                val value = try {
                    iService?.getTime() ?: ""
                } catch (e: Exception) {
                    0f
                }
                _time.value = value as String
                Log.d(TAG, "time  is $value")
                delay(200)
            }
        }
    }

    fun fetchMode() {
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                val value = try {
                    iService?.getMode() ?: ""
                } catch (e: Exception) {
                    0f
                }
                _mode.value = value as Boolean
                Log.d(TAG, "mode  is $value")
                delay(200)
            }
        }
    }

    fun fetchUnit() {
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                val value = try {
                    iService?.getUnit() ?: ""
                } catch (e: Exception) {
                    0f
                }
                _unit.value = value as String
                Log.d(TAG, "mode  is $value")
                delay(200)
            }
        }
    }

    fun fetchTotalDistance() {
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                val value = try {
                    iService?.getTotalDistance() ?: 0f
                } catch (e: Exception) {
                    0f
                }
                _totalDistance.value = value
                Log.d(TAG, "mode  is $value")
                delay(200)
            }
        }
    }

    fun fetchTotalTime() {
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                val value = try {
                    iService?.getTotalTime() ?: 0f
                } catch (e: Exception) {
                    0f
                }
                _totalTime.value = value
                Log.d(TAG, "mode  is $value")
                delay(200)
            }
        }
    }

    fun pickNewTrip(){
        val pair = cities.shuffled().take(2)
        _startLocation.value = pair[0]
        _destination.value = pair[1]
    }
}
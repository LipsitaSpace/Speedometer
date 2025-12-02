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

class DashboardViewModel : ViewModel() {
    private var iService : ISimulatorInterface? = null

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
        iService?.let { service ->
            viewModelScope.launch(Dispatchers.IO) {
                val bundle = Bundle()
                service.getData(bundle)
                _data.value = bundle
            }
        }
    }
}
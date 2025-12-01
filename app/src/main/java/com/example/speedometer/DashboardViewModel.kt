package com.example.speedometer

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.simulatorservice.ISimulatorInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(app: Application) : AndroidViewModel(app) {
    private var iService : ISimulatorInterface? = null

    private val _data = MutableStateFlow<Bundle?>(null)
    val data: StateFlow<Bundle?> = _data

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            iService = ISimulatorInterface.Stub.asInterface(service)
            fetchData()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            iService = null
        }
    }

    fun bindService() {
        val intent = Intent("com.example.simulatorservice.SimulatorService").apply {
            `package` = "com.example.simulatorservice"
        }
        getApplication<Application>().bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    fun unbindService() {
        getApplication<Application>().unbindService(connection)
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
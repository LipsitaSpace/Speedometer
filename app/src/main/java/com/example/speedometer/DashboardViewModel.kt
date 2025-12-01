package com.example.speedometer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
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

    fun bindService(context: Context) {
        val intent = Intent("com.example.simulatorservice.SimulatorService").apply {
            `package` = "com.example.simulatorservice"
        }
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    fun unbindService(context: Context) {
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
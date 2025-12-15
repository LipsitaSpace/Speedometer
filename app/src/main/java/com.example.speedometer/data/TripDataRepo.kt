package com.example.speedometer.data

import android.util.Log
import javax.inject.Inject

class TripDataRepo @Inject constructor(
    private val dao : TripDataDao
) {
    private var TAG = "TripDataRepo"

    suspend fun saveTripData(sLoc : String, sTime : String, eLoc: String , eTime : String) {
        Log.d(TAG,"saveVehicleData()")
        val data = TripData(startLocation = sLoc, startTime = sTime, destination = eLoc, endTime = eTime)
        dao.insertTripData(data)
        printLatestTripData()
    }

    suspend fun printLatestTripData() {
        val data = dao.getLatestData()
        Log.d(TAG, "Latest DB Data: $data")
    }
}
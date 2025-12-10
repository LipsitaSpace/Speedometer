package com.example.speedometer.data

import android.content.Context
import android.util.Log
import androidx.room.Room

class TripDataRepo(context : Context) {

    private val db = Room.databaseBuilder(
        context.applicationContext,
        TripDatabase::class.java,
        "trip_data"
    ).fallbackToDestructiveMigration(false).build()

    private val dao = db.TripDataDao()
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
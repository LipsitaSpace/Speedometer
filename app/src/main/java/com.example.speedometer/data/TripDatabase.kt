package com.example.speedometer.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TripData::class], version = 2)
abstract class TripDatabase : RoomDatabase(){
    abstract fun TripDataDao() : TripDataDao
}
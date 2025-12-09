package com.example.speedometer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trip_data")
data class TripData(
    @PrimaryKey(autoGenerate = true) val user : Int = 0,
    val startLocation : String,
    val startTime : String,
    val destination : String,
    val endTime : String
)
//package com.example.speedometer.data
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//import androidx.room.Update
//
//@Dao
//interface TripDataDao{
//
//    @Insert
//    suspend fun insertTripData(data : TripData)
//    @Update
//    suspend fun updateTripData(data : TripData)
//    @Query("select * from trip_data ORDER BY user DESC LIMIT 1")
//    suspend fun getLatestData(): TripData?
//}
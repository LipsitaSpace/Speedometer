package com.example.speedometer.data

class FakeTripDataDao : TripDataDao {

    var insertedData: TripData? = null

    override suspend fun insertTripData(data: TripData) {
        insertedData = data
    }

    override suspend fun updateTripData(data: TripData) {
        insertedData = data
    }

    override suspend fun getLatestData(): TripData? {
        return insertedData
    }
}

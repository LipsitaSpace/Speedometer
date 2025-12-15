package com.example.speedometer.hilt

import android.content.Context
import androidx.room.Room
import com.example.speedometer.data.TripDataDao
import com.example.speedometer.data.TripDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context : Context): TripDatabase{
        return Room.databaseBuilder(
            context,
            TripDatabase::class.java,
            "trip_data"
        ).fallbackToDestructiveMigration().build()
    }
    @Provides
    @Singleton
    fun provideTripDao(db: TripDatabase): TripDataDao =db.TripDataDao()
}
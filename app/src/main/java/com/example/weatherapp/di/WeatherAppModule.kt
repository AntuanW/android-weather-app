package com.example.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.model.database.WeatherAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherAppModule {

    @Provides
    @Singleton
    fun provideWeatherAppDatabase(@ApplicationContext appContext: Context): WeatherAppDatabase {
        return Room.databaseBuilder(
            context = appContext,
            klass = WeatherAppDatabase::class.java,
            name = WeatherAppDatabase.DB_NAME
        ).build()
    }
}
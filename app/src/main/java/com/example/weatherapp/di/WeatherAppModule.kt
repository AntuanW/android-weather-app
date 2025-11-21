package com.example.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.model.database.WeatherAppDatabase
import com.example.weatherapp.model.dto.WeatherSummaryFactory
import com.example.weatherapp.model.service.WeatherService
import com.example.weatherapp.model.service.WeatherSummaryService
import com.example.weatherapp.model.service.client.WeatherApiInterface
import com.example.weatherapp.model.service.location.CurrentLocationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Provides
    @Singleton
    fun provideRetrofitInstance(): WeatherApiInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(WeatherApiInterface::class.java)
    }
    
    @Provides
    @Singleton
    fun provideWeatherService(api: WeatherApiInterface, factory: WeatherSummaryFactory): WeatherService {
        return WeatherService(api, factory)
    }

    @Provides
    @Singleton
    fun provideWeatherSummaryService(weatherService: WeatherService): WeatherSummaryService {
        return WeatherSummaryService(weatherService)
    }

    @Provides
    @Singleton
    fun provideWeatherSummaryFactory(): WeatherSummaryFactory {
        return WeatherSummaryFactory()
    }

    @Provides
    @Singleton
    fun provideCurrentLocationService(@ApplicationContext appContext: Context): CurrentLocationService {
        return CurrentLocationService(appContext)
    }
}
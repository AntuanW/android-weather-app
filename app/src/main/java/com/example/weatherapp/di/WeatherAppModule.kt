package com.example.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.model.database.WeatherAppDatabase
import com.example.weatherapp.model.service.client.WeatherApiInterface
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
}
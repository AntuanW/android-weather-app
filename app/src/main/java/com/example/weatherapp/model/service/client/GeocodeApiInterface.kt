package com.example.weatherapp.model.service.client

import com.example.weatherapp.model.service.response.location.GeocodeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodeApiInterface {

    @GET("direct")
    suspend fun getCoordinatesByName(
        @Query("appid") appid: String,
        @Query("q") location: String,
        @Query("limit") limit: Int
    ) : List<GeocodeResponse>

    @GET("reverse")
    suspend fun getNameByCoordinates(
        @Query("appid") appid: String,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("limit") limit: Int
    ) : List<GeocodeResponse>
}
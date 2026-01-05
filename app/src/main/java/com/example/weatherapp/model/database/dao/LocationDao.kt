package com.example.weatherapp.model.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.model.database.model.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(location: Location)

    @Delete
    fun delete(location: Location)

    @Query(value = "DELETE FROM location WHERE latitude = :latitude AND longitude = :longitude")
    fun deleteByCoords(latitude: Double, longitude: Double)

    @Query("SELECT * FROM location ORDER BY name")
    fun findAll(): Flow<List<Location>>

    @Query("SELECT * FROM location ORDER BY name")
    fun findAllFavs(): List<Location>

    @Query("SELECT EXISTS(SELECT 1 FROM location WHERE latitude = :latitude AND longitude = :longitude)")
    suspend fun exists(latitude: Double, longitude: Double): Boolean
}
package com.example.weatherapp.model.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherapp.model.database.model.Location

@Dao
interface LocationDao {

    @Insert
    fun insert(location: Location)

    @Delete
    fun delete(location: Location)

    @Query("SELECT * FROM location")
    fun findAll(): List<Location>
}
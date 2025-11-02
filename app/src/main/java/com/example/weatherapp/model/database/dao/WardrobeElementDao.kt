package com.example.weatherapp.model.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherapp.model.database.model.WardrobeElement

@Dao
interface WardrobeElementDao {

    @Insert
    fun insert(element: WardrobeElement)

    @Delete
    fun delete(element: WardrobeElement)

    @Query("SELECT * FROM wardrobe")
    fun findAll(): WardrobeElement
}
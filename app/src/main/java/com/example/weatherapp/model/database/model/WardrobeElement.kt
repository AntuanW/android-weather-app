package com.example.weatherapp.model.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.model.dto.wardrobe.OutfitType
import com.example.weatherapp.model.dto.weather.Temperature

@Entity(tableName = "wardrobe")
data class WardrobeElement(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "temperature_type")
    val temperature: Temperature,

    @ColumnInfo(name = "outfit_type")
    val outfitType: OutfitType,

    @ColumnInfo(name = "image_path")
    val imagePath: String,

    @ColumnInfo(name = "waterproof")
    val waterProof: Boolean,
)
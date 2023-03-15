package com.example.weatherapp.data.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "weather")
class WeatherDBEntity(
    @PrimaryKey
    @ColumnInfo(name = "city_id")
    val cityId: String,
    @ColumnInfo(name = "icon_path")
    val iconPath: String,
    val temperature: Double,
    val description: String,
    @ColumnInfo(name = "temp_min")
    val tempMin: Double,
    @ColumnInfo(name = "temp_max")
    val tempMax: Double,
    @ColumnInfo(name = "wind_speed")
    val windSpeed: Double,
    val humidity: Int,
    @ColumnInfo(name = "last_remote_fetch")
    val lastRemoteFetch: Long,
    val sunrise: Calendar,
    val sunset: Calendar,
)
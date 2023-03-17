package com.example.weatherapp.data.datasource.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.data.repository.entity.WeatherDBEntity
import java.util.*

@Entity(tableName = DatabaseContract.TABLE_NAME)
class WeatherDBEntityImpl(
    @PrimaryKey
    @ColumnInfo(name = DatabaseContract.Columns.ID)
    override val cityId: String,
    @ColumnInfo(name = DatabaseContract.Columns.ICON_PATH)
    override val iconPath: String,
    override val temperature: Double,
    override val description: String,
    @ColumnInfo(name = DatabaseContract.Columns.TEMP_MIN)
    override val tempMin: Double,
    @ColumnInfo(name = DatabaseContract.Columns.TEMP_MAX)
    override val tempMax: Double,
    @ColumnInfo(name = DatabaseContract.Columns.WIND_SPEED)
    override val windSpeed: Double,
    override val humidity: Int,
    @ColumnInfo(name = DatabaseContract.Columns.LAST_REMOTE_FETCH)
    override val lastRemoteFetch: Long,
    override val sunrise: Calendar,
    override val sunset: Calendar,
) : WeatherDBEntity(
    cityId,
    iconPath,
    temperature,
    description,
    tempMin,
    tempMax,
    windSpeed,
    humidity,
    lastRemoteFetch,
    sunrise,
    sunset
)

fun WeatherDBEntity.toImpl() = WeatherDBEntityImpl(
    cityId,
    iconPath,
    temperature,
    description,
    tempMin,
    tempMax,
    windSpeed,
    humidity,
    lastRemoteFetch,
    sunrise,
    sunset
)
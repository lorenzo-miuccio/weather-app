package com.example.weatherapp.data.repository.entity

import com.example.weatherapp.data.datasource.database.WeatherDBEntityImpl
import java.util.*

open class WeatherDBEntity(
    open val cityId: String,
    open val iconPath: String,
    open val temperature: Double,
    open val description: String,
    open val tempMin: Double,
    open val tempMax: Double,
    open val windSpeed: Double,
    open val humidity: Int,
    open val lastRemoteFetch: Long,
    open val sunrise: Calendar,
    open val sunset: Calendar
) {
    fun prova() {
        print("ciao")
    }
}
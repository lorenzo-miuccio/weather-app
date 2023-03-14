package com.example.weatherapp.model

import java.util.*

data class Weather(
    val iconPath: String,
    val temperature: Double,
    val description: String,
    val tempMin: Double,
    val tempMax: Double,
    val windSpeed: Double,
    val humidity: Int,
    val sunrise: Calendar,
    val sunset: Calendar
)
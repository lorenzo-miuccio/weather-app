package com.example.weatherapp.domain

import com.example.weatherapp.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeatherByCityId(cityId: String, forceRemoteFetch: Boolean): Weather
}
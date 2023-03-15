package com.example.weatherapp.data.repository.interfaces

import com.example.weatherapp.data.datasource.remote.apiresponse.WeatherApiResp

interface WeatherRemoteDataSource {
    suspend fun getWeatherByCityId(cityId: String): WeatherApiResp
}
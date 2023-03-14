package com.example.weatherapp.datasource.remote

import com.example.weatherapp.datasource.WeatherApiService
import com.example.weatherapp.datasource.remote.apiresponse.WeatherApiResp

class WeatherRemoteDatasource(private val api: WeatherApiService) {
    suspend fun getWeatherByCityId(cityId: String): WeatherApiResp = api.getWeatherByCityId(cityId)
}
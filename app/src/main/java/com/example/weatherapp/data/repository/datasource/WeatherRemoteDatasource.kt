package com.example.weatherapp.data.repository.datasource

import com.example.weatherapp.data.service.remote.apiresponse.WeatherApiResp

interface WeatherRemoteDatasource {
    suspend fun getWeatherByCityId(cityId: String): WeatherApiResp
}
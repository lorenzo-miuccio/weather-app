package com.example.weatherapp.data.service.remote

import com.example.weatherapp.data.repository.datasource.WeatherRemoteDatasource
import com.example.weatherapp.service.WeatherApiService
import com.example.weatherapp.data.service.remote.apiresponse.WeatherApiResp

class WeatherRemoteDatasourceImpl(private val api: WeatherApiService) : WeatherRemoteDatasource {
    override suspend fun getWeatherByCityId(cityId: String): WeatherApiResp = api.getWeatherByCityId(cityId)
}
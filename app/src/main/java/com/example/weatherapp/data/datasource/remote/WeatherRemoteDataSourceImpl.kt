package com.example.weatherapp.data.datasource.remote

import com.example.weatherapp.data.repository.interfaces.WeatherRemoteDataSource
import com.example.weatherapp.service.WeatherApiService
import com.example.weatherapp.data.repository.entity.WeatherApiResp

class WeatherRemoteDataSourceImpl(private val api: WeatherApiService) : WeatherRemoteDataSource {
    override suspend fun getWeatherByCityId(cityId: String): WeatherApiResp = api.getWeatherByCityId(cityId)
}
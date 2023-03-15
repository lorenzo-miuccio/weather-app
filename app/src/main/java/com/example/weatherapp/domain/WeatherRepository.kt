package com.example.weatherapp.domain

import com.example.weatherapp.data.datasource.remote.apiresponse.WeatherApiResp
import com.example.weatherapp.domain.model.Weather

interface WeatherRepository {

    suspend fun getWeatherByCityId(cityId: String, forceRemoteFetch: Boolean): Weather
//    suspend fun getWeatherFromDatabase(): Weather
//
//    suspend fun getWeatherRespFromApi(): WeatherApiResp
//    fun isDataValid(): Boolean
}
package com.example.weatherapp.data.repository.datasource

import com.example.weatherapp.data.service.database.WeatherDBEntity

interface WeatherLocalDatasource {
    fun getWeatherByCityId(cityId: String): WeatherDBEntity

    fun insertWeather(weather: WeatherDBEntity)

    fun getLastRemoteFetch(cityId: String): Long?
}
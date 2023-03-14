package com.example.weatherapp.datasource.database

import com.example.weatherapp.model.Weather

class WeatherLocalDatasource(private val weatherDao: WeatherDao) {
    fun getWeatherByCityId(cityId: String): WeatherDBEntity? = weatherDao.findWeatherByCityId(cityId)

    fun insertWeather(weather: WeatherDBEntity) = weatherDao.insertWeather(weather = weather)
}
package com.example.weatherapp.data.datasource.database

import com.example.weatherapp.data.repository.entity.WeatherDBEntity
import com.example.weatherapp.data.repository.interfaces.WeatherLocalDataSource

class WeatherLocalDataSourceImpl(private val weatherDao: WeatherDao): WeatherLocalDataSource {
    override fun getWeatherByCityId(cityId: String): WeatherDBEntity = weatherDao.findWeatherByCityId(cityId)

    override fun insertWeather(weather: WeatherDBEntity) = weatherDao.insertWeather(weather)

    override fun getLastRemoteFetch(cityId: String) = weatherDao.findLastRemoteFetchByCityId(cityId)
}
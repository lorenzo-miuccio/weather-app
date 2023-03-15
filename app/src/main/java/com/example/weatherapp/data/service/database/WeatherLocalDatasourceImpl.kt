package com.example.weatherapp.data.service.database

import com.example.weatherapp.data.repository.datasource.WeatherLocalDatasource

class WeatherLocalDatasourceImpl(private val weatherDao: WeatherDao): WeatherLocalDatasource {
    override fun getWeatherByCityId(cityId: String): WeatherDBEntity = weatherDao.findWeatherByCityId(cityId)

    override fun insertWeather(weather: WeatherDBEntity) = weatherDao.insertWeather(weather)

    override fun getLastRemoteFetch(cityId: String) = weatherDao.findLastRemoteFetchByCityId(cityId)
}
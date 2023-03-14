package com.example.weatherapp.datasource.database

class WeatherLocalDatasource(private val weatherDao: WeatherDao) {
    fun getWeatherByCityId(cityId: String): WeatherDBEntity = weatherDao.findWeatherByCityId(cityId)

    fun insertWeather(weather: WeatherDBEntity) = weatherDao.insertWeather(weather)

    fun getLastRemoteFetch(cityId: String) = weatherDao.findLastRemoteFetchByCityId(cityId)
}
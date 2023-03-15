package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.data.service.CityKeyValueDatasourceImpl
import com.example.weatherapp.service.WeatherApi
import com.example.weatherapp.data.service.database.WeatherDatabase
import com.example.weatherapp.data.service.database.WeatherLocalDatasourceImpl
import com.example.weatherapp.data.service.remote.WeatherRemoteDatasourceImpl
import com.example.weatherapp.data.repository.WeatherRepository

private const val PREFS_NAME = "selected_city"

class MyApplication : Application() {

    val weatherRepository: WeatherRepository by lazy {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val dao = WeatherDatabase.getDatabase(this).weatherDao()
        return@lazy WeatherRepository(
            weatherRemoteDatasource = WeatherRemoteDatasourceImpl(WeatherApi.retrofitService),
            keyValueDatasource = CityKeyValueDatasourceImpl(sharedPreferences),
            weatherLocalDatasource = WeatherLocalDatasourceImpl(dao)
        )
    }
}
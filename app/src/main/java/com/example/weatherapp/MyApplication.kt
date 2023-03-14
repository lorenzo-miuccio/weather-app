package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.datasource.CityKeyValueDatasource
import com.example.weatherapp.datasource.WeatherApi
import com.example.weatherapp.datasource.database.WeatherDatabase
import com.example.weatherapp.datasource.database.WeatherLocalDatasource
import com.example.weatherapp.datasource.remote.WeatherRemoteDatasource
import com.example.weatherapp.repository.WeatherRepository

private const val PREFS_NAME = "selected_city"

class MyApplication : Application() {

    val weatherRepository: WeatherRepository by lazy {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val dao = WeatherDatabase.getDatabase(this).weatherDao()
        return@lazy WeatherRepository(
            weatherRemoteDatasource = WeatherRemoteDatasource(WeatherApi.retrofitService),
            keyValueDatasource = CityKeyValueDatasource(sharedPreferences),
            weatherLocalDatasource = WeatherLocalDatasource(dao)
        )
    }
}
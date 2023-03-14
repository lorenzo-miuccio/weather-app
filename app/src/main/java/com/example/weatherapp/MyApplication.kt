package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.datasource.CityKeyValueDatasource
import com.example.weatherapp.datasource.WeatherApi
import com.example.weatherapp.datasource.database.WeatherDatabase
import com.example.weatherapp.repository.WeatherRepository

private const val PREFS_NAME = "selected_city"

class MyApplication : Application() {

    val weatherRepository: WeatherRepository by lazy {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val db = WeatherDatabase.getDatabase(this)
        return@lazy WeatherRepository(
            api = WeatherApi.retrofitService,
            keyValueDatasource = CityKeyValueDatasource(sharedPreferences)
        )
    }
}
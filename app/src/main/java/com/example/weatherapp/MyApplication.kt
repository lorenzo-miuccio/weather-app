package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.datasource.CityKeyValueDatasource
import com.example.weatherapp.datasource.WeatherApi
import com.example.weatherapp.repository.WeatherRepository

private const val PREFS_NAME = "selected_city"

class MyApplication : Application() {
    private lateinit var weatherRepository: WeatherRepository

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        weatherRepository = WeatherRepository(
            api = WeatherApi.retrofitService,
            keyValueDatasource = CityKeyValueDatasource(sharedPreferences)
        )
    }
}
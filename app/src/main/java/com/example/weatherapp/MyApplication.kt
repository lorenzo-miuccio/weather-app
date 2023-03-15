package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.data.datasource.CityKeyValueDataSourceImpl
import com.example.weatherapp.service.WeatherApi
import com.example.weatherapp.data.datasource.database.WeatherDatabase
import com.example.weatherapp.data.datasource.database.WeatherLocalDataSourceImpl
import com.example.weatherapp.data.datasource.remote.WeatherRemoteDataSourceImpl
import com.example.weatherapp.data.repository.WeatherRepository

private const val PREFS_NAME = "selected_city"

class MyApplication : Application() {

    val weatherRepository: WeatherRepository by lazy {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val dao = WeatherDatabase.getDatabase(this).weatherDao()
        return@lazy WeatherRepository(
            weatherRemoteDatasource = WeatherRemoteDataSourceImpl(WeatherApi.retrofitService),
            keyValueDatasource = CityKeyValueDataSourceImpl(sharedPreferences),
            weatherLocalDatasource = WeatherLocalDataSourceImpl(dao)
        )
    }
}
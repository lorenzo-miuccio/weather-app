package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.data.repository.CityRepositoryImpl
import com.example.weatherapp.data.datasource.CityDataSourceImpl
import com.example.weatherapp.service.WeatherApi
import com.example.weatherapp.data.datasource.database.WeatherDatabase
import com.example.weatherapp.data.datasource.database.WeatherLocalDataSourceImpl
import com.example.weatherapp.data.datasource.remote.WeatherRemoteDataSourceImpl
import com.example.weatherapp.domain.CityRepository
import com.example.weatherapp.domain.WeatherRepository
import com.example.weatherapp.data.repository.WeatherRepositoryImpl

private const val PREFS_NAME = "selected_city"

class MyApplication : Application() {

    val weatherRepository: WeatherRepository by lazy {
        val dao = WeatherDatabase.getDatabase(this).weatherDao()
        return@lazy WeatherRepositoryImpl(
            weatherRemoteDatasource = WeatherRemoteDataSourceImpl(WeatherApi.retrofitService),
            weatherLocalDatasource = WeatherLocalDataSourceImpl(dao)
        )
    }

    val cityRepository: CityRepository by lazy {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        return@lazy CityRepositoryImpl(
            CityDataSourceImpl(prefs)
        )
    }
}
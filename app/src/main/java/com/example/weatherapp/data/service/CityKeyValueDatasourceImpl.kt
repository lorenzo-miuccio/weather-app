package com.example.weatherapp.data.service

import android.content.SharedPreferences
import com.example.weatherapp.data.repository.datasource.CityKeyValueDatasource

private const val CITY_KEY = "cityId"

class CityKeyValueDatasourceImpl(private val prefs: SharedPreferences) : CityKeyValueDatasource {

    override fun setSelectedCity(cityId: String) {
        prefs.edit().putString(CITY_KEY, cityId).apply()
    }

    override fun getSelectedCity(): String? {
        return prefs.getString(CITY_KEY, null)
    }
}
package com.example.weatherapp.data.datasource

import android.content.SharedPreferences
import com.example.weatherapp.data.repository.interfaces.CityKeyValueDataSource

private const val CITY_KEY = "cityId"

class CityKeyValueDataSourceImpl(private val prefs: SharedPreferences) : CityKeyValueDataSource {

    override fun setSelectedCity(cityId: String) {
        prefs.edit().putString(CITY_KEY, cityId).apply()
    }

    override fun getSelectedCity(): String? {
        return prefs.getString(CITY_KEY, null)
    }
}
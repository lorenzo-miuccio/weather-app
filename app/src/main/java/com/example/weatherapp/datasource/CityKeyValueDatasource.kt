package com.example.weatherapp.datasource

import android.content.SharedPreferences

private const val CITY_KEY = "cityId"

class CityKeyValueDatasource(private val prefs: SharedPreferences) {

    fun setSelectedCity(cityId: String) {
        prefs.edit().putString(CITY_KEY, cityId).apply()
    }

    fun getSelectedCity(): String? {
        return prefs.getString(CITY_KEY, null)
    }
}
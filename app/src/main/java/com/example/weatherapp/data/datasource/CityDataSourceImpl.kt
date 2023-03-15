package com.example.weatherapp.data.datasource

import android.content.SharedPreferences
import com.example.weatherapp.data.repository.interfaces.CityDataSource

private const val CITY_KEY = "cityId"

class CityDataSourceImpl(private val prefs: SharedPreferences) : CityDataSource {

    override fun setSelectedCityId(cityId: String) {
        prefs.edit().putString(CITY_KEY, cityId).apply()
    }

    override fun getSelectedCityId(): String? {
        return prefs.getString(CITY_KEY, null)
    }
}
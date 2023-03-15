package com.example.weatherapp.data.repository.interfaces

interface CityDataSource {
    fun setSelectedCityId(cityId: String)
    fun getSelectedCityId(): String?
}
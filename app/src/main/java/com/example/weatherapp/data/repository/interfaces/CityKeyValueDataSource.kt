package com.example.weatherapp.data.repository.interfaces

interface CityKeyValueDataSource {
    fun setSelectedCity(cityId: String)
    fun getSelectedCity(): String?
}
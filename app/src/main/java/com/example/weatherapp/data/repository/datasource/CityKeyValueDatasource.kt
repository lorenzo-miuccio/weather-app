package com.example.weatherapp.data.repository.datasource

interface CityKeyValueDatasource {
    fun setSelectedCity(cityId: String)
    fun getSelectedCity(): String?
}
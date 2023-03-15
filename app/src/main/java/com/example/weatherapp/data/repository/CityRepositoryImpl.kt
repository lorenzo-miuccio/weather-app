package com.example.weatherapp.data.repository

import com.example.weatherapp.data.repository.interfaces.CityKeyValueDataSource
import com.example.weatherapp.domain.CityRepository
import com.example.weatherapp.domain.model.City
import com.example.weatherapp.domain.model.cityList

class CityRepositoryImpl(private val keyValueDatasource: CityKeyValueDataSource) : CityRepository {
    override var selectedCity: City
        get() {
            val cityId = keyValueDatasource.getSelectedCity()

            val selectedCity = cityList.firstOrNull {
                it.id == cityId
            }
            return selectedCity ?: cityList[0]
        }
        set(city) = keyValueDatasource.setSelectedCity(city.id)
}
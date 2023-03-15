package com.example.weatherapp.data.repository

import com.example.weatherapp.data.repository.interfaces.CityDataSource
import com.example.weatherapp.domain.CityRepository
import com.example.weatherapp.domain.model.City
import com.example.weatherapp.domain.model.cityList

class CityRepositoryImpl(private val keyValueDatasource: CityDataSource) : CityRepository {
    override var selectedCity: City
        get() {
            val cityId = keyValueDatasource.getSelectedCityId()

            val selectedCity = cityList.firstOrNull {
                it.id == cityId
            }
            return selectedCity ?: cityList[0]
        }
        set(city) = keyValueDatasource.setSelectedCityId(city.id)
}
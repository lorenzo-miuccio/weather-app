package com.example.weatherapp.data.repository

import com.example.weatherapp.data.repository.entity.WeatherDBEntity
import com.example.weatherapp.data.repository.entity.WeatherApiResp
import com.example.weatherapp.domain.WeatherRepository
import com.example.weatherapp.data.repository.interfaces.WeatherLocalDataSource
import com.example.weatherapp.data.repository.interfaces.WeatherRemoteDataSource
import com.example.weatherapp.domain.model.Weather
import java.util.*

private const val VALIDITY_IN_MILLISECONDS: Long = 15000

class WeatherRepositoryImpl(
    private val weatherRemoteDatasource: WeatherRemoteDataSource,
    private val weatherLocalDatasource: WeatherLocalDataSource,
) : WeatherRepository {

    override suspend fun getWeatherByCityId(cityId: String, forceRemoteFetch: Boolean): Weather {
        if (isCityDataValid(cityId) && !forceRemoteFetch) {
            return getWeatherFromDatabase(cityId)
        }
        weatherLocalDatasource.insertWeather(getWeatherRespFromApi(cityId).toDBEntity())
        return getWeatherFromDatabase(cityId)
    }

    private fun getWeatherFromDatabase(cityId: String): Weather =
        weatherLocalDatasource.getWeatherByCityId(cityId).toWeatherModel()

    private suspend fun getWeatherRespFromApi(cityId: String): WeatherApiResp =
        weatherRemoteDatasource.getWeatherByCityId(cityId)

    private fun isCityDataValid(cityId: String): Boolean {
        val lastFetchInMilliseconds =
            weatherLocalDatasource.getLastRemoteFetch(cityId) ?: 0
        return (System.currentTimeMillis() - lastFetchInMilliseconds) < VALIDITY_IN_MILLISECONDS
    }
}

private fun WeatherApiResp.toDBEntity(): WeatherDBEntity {
    val iconPath = "https://openweathermap.org/img/wn/" + weather[0].iconPath + "@2x.png"

    sunTimes.sunrise.add(Calendar.SECOND, timezoneInSeconds)
    sunTimes.sunset.add(Calendar.SECOND, timezoneInSeconds)

    return WeatherDBEntity(
        cityId = "$cityName, ${sunTimes.country}",
        iconPath = iconPath,
        temperature = main.temp,
        tempMin = main.tempMin,
        description = weather[0].description,
        tempMax = main.tempMax,
        humidity = main.humidity,
        windSpeed = wind.speed * 3.6,
        sunrise = sunTimes.sunrise,
        sunset = sunTimes.sunset,
        lastRemoteFetch = System.currentTimeMillis()
    )
}

private fun WeatherDBEntity.toWeatherModel() = Weather(
    iconPath = iconPath,
    temperature = temperature,
    tempMin = tempMin,
    description = description,
    tempMax = tempMax,
    humidity = humidity,
    windSpeed = windSpeed,
    sunrise = sunrise,
    sunset = sunset,
    secondsSinceLastFetch = ((System.currentTimeMillis() - lastRemoteFetch) / 1000).toInt()
)
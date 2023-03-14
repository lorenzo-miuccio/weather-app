package com.example.weatherapp.repository

import androidx.room.Database
import com.example.weatherapp.datasource.CityKeyValueDatasource
import com.example.weatherapp.datasource.database.WeatherDBEntity
import com.example.weatherapp.datasource.database.WeatherLocalDatasource
import com.example.weatherapp.datasource.remote.WeatherRemoteDatasource
import com.example.weatherapp.datasource.remote.apiresponse.WeatherApiResp
import com.example.weatherapp.model.City
import com.example.weatherapp.model.Weather
import com.example.weatherapp.model.citiesList
import kotlinx.coroutines.CoroutineScope
import java.util.*

private const val VALIDITY_IN_MILLISECONDS: Long = 15000

class WeatherRepository(
    private val weatherRemoteDatasource: WeatherRemoteDatasource,
    private val weatherLocalDatasource: WeatherLocalDatasource,
    private val keyValueDatasource: CityKeyValueDatasource
) {

    var selectedCity: City
        get() {
            val cityId = keyValueDatasource.getSelectedCity()

            val selectedCity = citiesList.firstOrNull {
                it.id == cityId
            }
            return selectedCity ?: citiesList[0]
        }
        set(city) = keyValueDatasource.setSelectedCity(city.id)


    val millisecondsSinceLastFetch: Long
        get() {
            val lastFetchInMilliseconds =
                weatherLocalDatasource.getLastRemoteFetch(selectedCity.id) ?: 0
            return System.currentTimeMillis() - lastFetchInMilliseconds
        }


    suspend fun getWeather(forceRemoteFetch: Boolean): Weather {
        if (isDataValid() && !forceRemoteFetch) {
            return getWeatherFromDatabase()
        }
        weatherLocalDatasource.insertWeather(getWeatherRespFromApi().toDBEntity())
        return getWeatherFromDatabase()
    }

    private fun getWeatherFromDatabase(): Weather =
        weatherLocalDatasource.getWeatherByCityId(selectedCity.id).toEntity()

    private suspend fun getWeatherRespFromApi(): WeatherApiResp =
        weatherRemoteDatasource.getWeatherByCityId(selectedCity.id)

    private fun isDataValid() = millisecondsSinceLastFetch < VALIDITY_IN_MILLISECONDS

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

private fun WeatherDBEntity.toEntity() = Weather(
    iconPath = iconPath,
    temperature = temperature,
    tempMin = tempMin,
    description = description,
    tempMax = tempMax,
    humidity = humidity,
    windSpeed = windSpeed,
    sunrise = sunrise,
    sunset = sunset
)
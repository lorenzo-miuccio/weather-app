package com.example.weatherapp.data.service.remote.apiresponse

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson
import java.util.*

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class Date

data class WeatherApiResp(
    val weather: List<WeatherDescriptionEntity>,
    val main: MainWeatherEntity,
    val wind: WindEntity,
    @Json(name = "sys")
    val sunTimes: SunTimesEntity,
    @Json(name = "name")
    val cityName: String,
    @Json(name = "timezone")
    val timezoneInSeconds: Int
) {
    data class WeatherDescriptionEntity(
        @Json(name = "icon") val iconPath: String,
        @Json(name = "main") val description: String
    )

    data class WindEntity(val speed: Double)
    data class SunTimesEntity(val sunrise: Calendar, val sunset: Calendar, val country: String)
    data class MainWeatherEntity(
        val temp: Double,
        @Json(name = "temp_min") val tempMin: Double,
        @Json(name = "temp_max") val tempMax: Double,
        val humidity: Int
    )
}


internal class DateAdapter {
    @ToJson
    fun toJson(dateTime: Calendar): Long {
        return dateTime.timeInMillis
    }

    @FromJson
    fun fromJson(secondsSinceEpoch: Long): Calendar {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.timeInMillis = secondsSinceEpoch * 1000
        return calendar
    }
}
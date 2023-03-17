package com.example.weatherapp.data.datasource.database

import androidx.room.ColumnInfo

object DatabaseContract {
    const val TABLE_NAME = "weather"
    const val DATABASE_NAME = "weather_database"

    object Columns {
        const val ID = "city_id"
        const val ICON_PATH = "icon_path"
        const val TEMP_MIN = "temp_min"
        const val TEMP_MAX = "temp_max"
        const val WIND_SPEED = "wind_speed"
        const val LAST_REMOTE_FETCH = "last_remote_fetch"
    }
}
/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.weatherapp.data.datasource.database

import androidx.room.*

/**
 * Data Access Object for database interaction.
 */
@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE city_id = :id")
    fun findWeatherByCityId(id: String): WeatherDBEntity

    @Query("SELECT last_remote_fetch FROM weather WHERE city_id = :id")
    fun findLastRemoteFetchByCityId(id: String): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather: WeatherDBEntity)
}

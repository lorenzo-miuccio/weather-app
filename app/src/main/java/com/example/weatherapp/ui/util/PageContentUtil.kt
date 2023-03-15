package com.example.weatherapp.ui.util

import android.view.View
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.ui.state.WeatherFetchState


interface PageContentUtil {

    fun bindView(weather: Weather)

    fun setPageContent(
        loadingView: View,
        dataView: View,
        errorView: View,
        fetchState: WeatherFetchState,
    ) {
        when (fetchState) {
            is WeatherFetchState.Loading -> {
                loadingView.visibility = View.VISIBLE
                dataView.visibility = View.GONE
                errorView.visibility = View.GONE
            }
            is WeatherFetchState.Success -> {
                loadingView.visibility = View.GONE
                dataView.visibility = View.VISIBLE
                errorView.visibility = View.GONE
                bindView(fetchState.weather)
            }
            is WeatherFetchState.Error -> {
                loadingView.visibility = View.GONE
                dataView.visibility = View.GONE
                errorView.visibility = View.VISIBLE
            }
        }
    }
}
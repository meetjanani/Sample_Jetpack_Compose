package com.example.sample_jetpack_compose.weatherApp.repository

import android.util.Log
import com.example.sample_jetpack_compose.weatherApp.data.DataOrExceptionWeatherApi
import com.example.sample_jetpack_compose.weatherApp.model.weatherApiModel.Weather
import com.example.sample_jetpack_compose.weatherApp.network.WeatherApi

class WeatherRepository(private val api: WeatherApi,) {

    suspend fun getWeather(cityQuery: String, units: String): DataOrExceptionWeatherApi<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(cityQuery, units = units)
        } catch (e: Exception) {
            Log.d("getWeather INSIDE ", "Error: ${e.message}")
            return DataOrExceptionWeatherApi(e = e)
        }
        return DataOrExceptionWeatherApi(data = response)
    }
}
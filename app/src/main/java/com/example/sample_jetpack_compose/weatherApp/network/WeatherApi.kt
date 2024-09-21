package com.example.sample_jetpack_compose.weatherApp.network

import com.example.sample_jetpack_compose.weatherApp.model.weatherApiModel.Weather
import com.example.sample_jetpack_compose.weatherApp.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query : String,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = API_KEY // your api key
                          ): Weather
}
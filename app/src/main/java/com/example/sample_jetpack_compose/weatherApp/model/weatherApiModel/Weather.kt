package com.example.sample_jetpack_compose.weatherApp.model.weatherApiModel

data class Weather(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherItem>,
    val message: Double
)
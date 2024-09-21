package com.example.sample_jetpack_compose.weatherApp.model.weatherApiModel

data class WeatherItem(
    val clouds: Int? = null,
    val deg: Int? = null,
    val dt: Int? = null,
    val feels_like: FeelsLike,
    val gust: Double? = null,
    val humidity: Int? = null,
    val pop: Double? = null,
    val pressure: Int? = null,
    val rain: Double? = null,
    val speed: Double? = null,
    val sunrise: Int? = null,
    val sunset: Int? = null,
    val temp: Temp? = null,
    val weather: List<WeatherObject>? = null
                      )
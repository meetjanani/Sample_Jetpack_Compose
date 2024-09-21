package com.example.sample_jetpack_compose.weatherApp.model.weatherApiModel

data class WeatherObject(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String)
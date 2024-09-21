package com.example.sample_jetpack_compose.weatherApp.model.weatherApiModel

data class City(
    val coord: Coord? = null,
    val country: String,
    val id: Int? = null,
    val name: String,
    val population: Int? = null,
    val timezone: Int? = null
)
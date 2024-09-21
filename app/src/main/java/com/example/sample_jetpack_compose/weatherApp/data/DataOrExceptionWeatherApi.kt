package com.example.sample_jetpack_compose.weatherApp.data

class DataOrExceptionWeatherApi<T, Boolean, E: Exception>(
    var data: T? = null,
    var loading: kotlin.Boolean? = null,
    var e: E? = null)
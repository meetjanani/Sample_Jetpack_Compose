package com.example.sample_jetpack_compose.weatherApp.screens.mainScreen

import androidx.lifecycle.ViewModel
import com.example.sample_jetpack_compose.weatherApp.data.DataOrExceptionWeatherApi
import com.example.sample_jetpack_compose.weatherApp.model.weatherApiModel.Weather
import com.example.sample_jetpack_compose.weatherApp.repository.WeatherRepository

class MainViewModel(private val repository: WeatherRepository, ) : ViewModel() {

    suspend fun getWeatherData(city: String, units: String): DataOrExceptionWeatherApi<Weather, Boolean, Exception> {
        return repository.getWeather(city, units)
    }


    /*val data: MutableState<DataOrExceptionWeatherApi<Weather, Boolean, Exception>> =
        mutableStateOf(DataOrExceptionWeatherApi(data = null, loading = true, e = Exception("")))

    init {
        loadWeather()
    }

    private fun loadWeather() {
        getWeatherData("Seattle")
    }


    private fun getWeatherData(
        city: String
    ) {
        viewModelScope.launch {
            if (city.isEmpty()) return@launch
            data.value.loading = true
            data.value = repository.getWeather(cityQuery = city)
            if (data.value.data.toString().isNotEmpty()) data.value.loading = false
        }
        Log.d("getWeather", "getWeather: ${data.value.data.toString()}")
    }*/
}
package com.example.sample_jetpack_compose.weatherApp.screens.favouriteScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample_jetpack_compose.weatherApp.model.roomEntity.FavouriteEntity
import com.example.sample_jetpack_compose.weatherApp.repository.WeatherDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class FavouriteViewModel(
    private val weatherDbRepository: WeatherDbRepository
) : ViewModel() {
    private val _favList = MutableStateFlow<List<FavouriteEntity>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDbRepository.getFavourites().distinctUntilChanged().collect { listOfFavs ->
                if (listOfFavs.isNullOrEmpty()) {
                    Log.d("Data", "EmptyData")
                } else {
                    _favList.value = listOfFavs
                    Log.d("Data", "${listOfFavs.size} Records")
                }

            }
        }
    }

    fun insertFavourite(favouriteEntity: FavouriteEntity) = viewModelScope.launch(Dispatchers.IO) {
        weatherDbRepository.insertFavourite(favouriteEntity)
    }

    fun updateFavourite(favouriteEntity: FavouriteEntity) = viewModelScope.launch(Dispatchers.IO) {
        weatherDbRepository.updateFavourite(favouriteEntity)
    }

    fun deleteFavourite(favouriteEntity: FavouriteEntity) = viewModelScope.launch(Dispatchers.IO) {
        weatherDbRepository.deleteFavourite(favouriteEntity)
    }

    fun getFavouriteById(city: String) = viewModelScope.launch(Dispatchers.IO) {
        weatherDbRepository.getFavouriteById(city)
    }

}
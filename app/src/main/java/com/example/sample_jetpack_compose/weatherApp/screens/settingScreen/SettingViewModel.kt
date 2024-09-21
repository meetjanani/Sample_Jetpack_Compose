package com.example.sample_jetpack_compose.weatherApp.screens.settingScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample_jetpack_compose.weatherApp.model.roomEntity.UnitEntity
import com.example.sample_jetpack_compose.weatherApp.repository.WeatherDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class SettingViewModel(
    private val weatherDbRepository: WeatherDbRepository
) : ViewModel() {
    private val _unitList = MutableStateFlow<List<UnitEntity>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDbRepository.getUnits().distinctUntilChanged().collect { listOfUnits ->
                    if (listOfUnits.isNullOrEmpty()) {

                    } else {
                        _unitList.value = listOfUnits
                    }

                }
        }
    }

    fun insertUnit(unitEntity: UnitEntity) = viewModelScope.launch { weatherDbRepository.insertUnit(unitEntity) }
    fun updateUnit(unitEntity: UnitEntity) = viewModelScope.launch { weatherDbRepository.updateUnit(unitEntity) }
    fun deleteUnit(unitEntity: UnitEntity) = viewModelScope.launch { weatherDbRepository.deleteUnit(unitEntity) }
    fun deleteAllUnits() = viewModelScope.launch { weatherDbRepository.deleteAllUnit() }
}
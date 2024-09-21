package com.example.sample_jetpack_compose.weatherApp.repository

import com.example.sample_jetpack_compose.weatherApp.data.WeatherDatabase
import com.example.sample_jetpack_compose.weatherApp.model.roomEntity.FavouriteEntity
import com.example.sample_jetpack_compose.weatherApp.model.roomEntity.UnitEntity
import kotlinx.coroutines.flow.Flow

class WeatherDbRepository(
    private val database: WeatherDatabase
) {
    fun getFavourites(): Flow<List<FavouriteEntity>> {
        return database.weatherDao().getFavourites()
    }

    suspend fun getFavouriteById(city: String) {
        database.weatherDao().getFavById(city)
    }

    suspend fun insertFavourite(favouriteEntity: FavouriteEntity) {
        database.weatherDao().insertFavourite(favouriteEntity)
    }

    suspend fun updateFavourite(favouriteEntity: FavouriteEntity) {
        database.weatherDao().updateFavourite(favouriteEntity)
    }

    suspend fun deleteFavourite(favouriteEntity: FavouriteEntity) {
        database.weatherDao().deleteFavourite(favouriteEntity)
    }

    fun getUnits(): Flow<List<UnitEntity>> {
        return database.unitDao().getUnits()
    }

    suspend fun insertUnit(unitEntity: UnitEntity) {
        database.unitDao().insertUnit(unitEntity)
    }

    suspend fun updateUnit(unitEntity: UnitEntity) {
        database.unitDao().updateUnit(unitEntity)
    }

    suspend fun deleteUnit(unitEntity: UnitEntity) {
        database.unitDao().deleteUnit(unitEntity)
    }

    suspend fun deleteAllUnit() {
        database.unitDao().deleteAllUnit()
    }
}
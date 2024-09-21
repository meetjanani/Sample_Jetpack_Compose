package com.example.sample_jetpack_compose.weatherApp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sample_jetpack_compose.weatherApp.model.roomEntity.FavouriteEntity
import com.example.sample_jetpack_compose.weatherApp.model.roomEntity.UnitEntity

@Database(
    entities = [FavouriteEntity::class, UnitEntity::class],
    version = 2, exportSchema = false,
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun unitDao(): UnitDao

}
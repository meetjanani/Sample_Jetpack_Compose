package com.example.sample_jetpack_compose.weatherApp.model.roomEntity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "fav_tbl")
data class FavouriteEntity(
    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "Country") // required only when we wanted to set different colume name in database
    val country: String
)
package com.example.sample_jetpack_compose.weatherApp.model.roomEntity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "settings_tbl")
data class UnitEntity(
    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "unit")
    val unit: String
)
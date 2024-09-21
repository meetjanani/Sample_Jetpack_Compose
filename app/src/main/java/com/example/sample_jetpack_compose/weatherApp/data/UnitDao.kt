package com.example.sample_jetpack_compose.weatherApp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.sample_jetpack_compose.weatherApp.model.roomEntity.UnitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UnitDao {
    @Query("Select * from settings_tbl")
    fun getUnits(): Flow<List<UnitEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unitEntity: UnitEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unitEntity: UnitEntity)

//    @Query("Delete from settings_tbl") // (OR)
    @Delete
    suspend fun deleteUnit(unitEntity: UnitEntity)


    @Query("Delete from settings_tbl")
    suspend fun deleteAllUnit()
}
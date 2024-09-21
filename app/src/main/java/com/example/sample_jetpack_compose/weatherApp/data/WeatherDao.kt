package com.example.sample_jetpack_compose.weatherApp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.sample_jetpack_compose.weatherApp.model.roomEntity.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("Select * from fav_tbl")
    fun getFavourites(): Flow<List<FavouriteEntity>>

    @Query("Select * from fav_tbl where city =:city")
    suspend fun getFavById(city: String): FavouriteEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favouriteEntity: FavouriteEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavourite(favouriteEntity: FavouriteEntity)

//    @Query("Delete from fav_tbl") // (OR)
    @Delete
    suspend fun deleteFavourite(favouriteEntity: FavouriteEntity)
}
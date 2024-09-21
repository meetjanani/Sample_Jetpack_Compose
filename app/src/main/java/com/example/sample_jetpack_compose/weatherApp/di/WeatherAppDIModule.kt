package com.example.sample_jetpack_compose.weatherApp.di

import androidx.room.Room
import com.example.sample_jetpack_compose.weatherApp.data.WeatherDao
import com.example.sample_jetpack_compose.weatherApp.data.WeatherDatabase
import com.example.sample_jetpack_compose.weatherApp.network.WeatherApi
import com.example.sample_jetpack_compose.weatherApp.repository.WeatherDbRepository
import com.example.sample_jetpack_compose.weatherApp.repository.WeatherRepository
import com.example.sample_jetpack_compose.weatherApp.screens.favouriteScreen.FavouriteViewModel
import com.example.sample_jetpack_compose.weatherApp.screens.mainScreen.MainViewModel
import com.example.sample_jetpack_compose.weatherApp.screens.settingScreen.SettingViewModel
import com.example.sample_jetpack_compose.weatherApp.utils.Constants
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val weatherAppDIModule = module {
    single { WeatherRepository(get()) }
    viewModel { MainViewModel(get()) }
    single {
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(WeatherApi::class.java)
    }

    single {
        Room.databaseBuilder(
            androidContext(), WeatherDatabase::class.java, "weather_database"
        ).fallbackToDestructiveMigration().build()
    }

    single { WeatherDbRepository(get()) }
    viewModel { FavouriteViewModel(get()) }
    viewModel { FavouriteViewModel(get()) }
    viewModel { SettingViewModel(get()) }
}
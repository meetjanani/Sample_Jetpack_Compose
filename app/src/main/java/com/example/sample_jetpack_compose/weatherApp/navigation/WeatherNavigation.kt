package com.example.sample_jetpack_compose.weatherApp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sample_jetpack_compose.weatherApp.screens.aboutScreen.AboutScreen
import com.example.sample_jetpack_compose.weatherApp.screens.favouriteScreen.FavouriteScreen
import com.example.sample_jetpack_compose.weatherApp.screens.mainScreen.MainWeatherScreen
import com.example.sample_jetpack_compose.weatherApp.screens.searchScreen.searchScreen
import com.example.sample_jetpack_compose.weatherApp.screens.settingScreen.SettingScreen
import com.example.sample_jetpack_compose.weatherApp.screens.splashScreen.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = WeatherScreen.SplashScreenRoute.name
    ) {
        composable(WeatherScreen.SplashScreenRoute.name) {
            WeatherSplashScreen(navController = navController)
        }

        // www.google.com/city="Rajkot"
        val route = WeatherScreen.MainWeatherScreenRoute.name
        composable(
            "$route/{city}", arguments = listOf(
                navArgument(name = "city", builder = {
                    type = NavType.StringType
                })
            )
        ) { navBack ->
            navBack.arguments?.getString("city").let { searchedCityName ->
                MainWeatherScreen(navController = navController, searchedCityName)
            }
        }

        composable(WeatherScreen.SearchScreenRoute.name) {
            searchScreen(navController = navController)
        }

        composable(WeatherScreen.AboutScreenRoute.name) {
            AboutScreen(navController = navController)
        }

        composable(WeatherScreen.FavouriteScreenRoute.name) {
            FavouriteScreen(navController = navController)
        }

        composable(WeatherScreen.SettingsScreenRoute.name) {
            SettingScreen(navController = navController)
        }
    }
}
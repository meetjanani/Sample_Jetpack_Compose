package com.example.sample_jetpack_compose.firebaseApp.navigation

import com.example.sample_jetpack_compose.firebaseApp.screen.FirebaseSplashScreen

enum class FirebaseScreens {
    SplashScreenFBApp,
    LoginScreenFBApp,
    CreateAccountScreenFBApp,
    HomeScreenFBApp,
    SearchScreenFBApp,
    DetailScreenFBApp,
    UpdateScreenFBApp,
    StatsScreenFBApp;

    companion object {
        fun fromRoute(route: String?): FirebaseScreens
        = when(route?.substringBefore("/")) {

            SplashScreenFBApp.name -> SplashScreenFBApp
            LoginScreenFBApp.name -> LoginScreenFBApp
            CreateAccountScreenFBApp.name -> CreateAccountScreenFBApp
            HomeScreenFBApp.name -> HomeScreenFBApp
            SearchScreenFBApp.name -> SearchScreenFBApp
            DetailScreenFBApp.name -> DetailScreenFBApp
            UpdateScreenFBApp.name -> UpdateScreenFBApp
            StatsScreenFBApp.name -> StatsScreenFBApp
            null -> HomeScreenFBApp
            else -> throw  IllegalArgumentException("Roure ${route} is not recognized")
        }
    }

}
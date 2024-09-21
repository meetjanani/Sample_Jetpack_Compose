package com.example.sample_jetpack_compose.firebaseApp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sample_jetpack_compose.firebaseApp.screen.FirebaseSplashScreen
import com.example.sample_jetpack_compose.firebaseApp.screen.details.FirebaseDetailsScreen
import com.example.sample_jetpack_compose.firebaseApp.screen.home.FirebaseHomeScreen
import com.example.sample_jetpack_compose.firebaseApp.screen.login.FirebaseLoginScreen
import com.example.sample_jetpack_compose.firebaseApp.screen.search.FirebaseSearchScreen
import com.example.sample_jetpack_compose.firebaseApp.screen.stats.FirebaseStatsScreen
import com.example.sample_jetpack_compose.firebaseApp.screen.update.FirebaseUpdateScreen

@Composable
fun FirebaseNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = FirebaseScreens.SplashScreenFBApp.name) {

        composable(FirebaseScreens.SplashScreenFBApp.name) {
            FirebaseSplashScreen(navController = navController)
        }

        composable(FirebaseScreens.LoginScreenFBApp.name) {
            FirebaseLoginScreen(navController = navController)
        }

        composable(FirebaseScreens.StatsScreenFBApp.name) {
            FirebaseStatsScreen(navController = navController)
        }

        composable(FirebaseScreens.HomeScreenFBApp.name) {
            FirebaseHomeScreen(navController = navController)
        }

        composable(FirebaseScreens.SearchScreenFBApp.name) {
            FirebaseSearchScreen(navController = navController)
        }

        val detailName = FirebaseScreens.DetailScreenFBApp.name
        composable("${detailName}/{bookId}", arguments = listOf(navArgument("bookId") {
            type = NavType.StringType
        })) {backStackEntry ->
            backStackEntry.arguments?.getString("bookId")?.let {bookIdValue ->
                FirebaseDetailsScreen(navController = navController, bookId = bookIdValue)
            }
        }

        val updateName = FirebaseScreens.UpdateScreenFBApp.name
        composable("${updateName}/{bookItemId}", arguments = listOf(navArgument("bookItemId") {
            type = NavType.StringType
        })) {backStackEntry ->
            backStackEntry.arguments?.getString("bookItemId")?.let {bookItemId ->
                FirebaseUpdateScreen(navController = navController, bookItemId = bookItemId)
            }
        }
    }
}
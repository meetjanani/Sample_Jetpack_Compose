package com.example.sample_jetpack_compose.weatherApp.screens.favouriteScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sample_jetpack_compose.weatherApp.model.roomEntity.FavouriteEntity
import com.example.sample_jetpack_compose.weatherApp.navigation.WeatherScreen
import com.example.sample_jetpack_compose.weatherApp.widgets.WeatherTopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavouriteScreen(
    navController: NavController, viewModel: FavouriteViewModel = koinViewModel()
) {
    Scaffold(topBar = {
        WeatherTopBar(
            navController = navController,
            title = "Favorite Cities",
            icon = Icons.Default.ArrowBack,
            isMainScreen = false,
        ) {
            navController.popBackStack()
        }
    }) {
        Surface(
            modifier = Modifier
                .padding(it)
                .padding(5.dp)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list = viewModel.favList.collectAsState().value
                LazyColumn {
                    items(items = list) {
                        CityRow(it, navController, viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun CityRow(
    favouriteEntity: FavouriteEntity, navController: NavController, viewModel: FavouriteViewModel
) {
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                navController.navigate(route = WeatherScreen.MainWeatherScreenRoute.name + "/${favouriteEntity.city}")
            }, shape = CircleShape.copy(topEnd = CornerSize(6.dp)), color = Color(0xFFB2DFDB)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = favouriteEntity.city, modifier = Modifier.padding(4.dp)
            )

            Surface(
                modifier = Modifier.padding(0.dp), shape = CircleShape, color = Color(0xFFD1E3E1)
            ) {
                Text(
                    text = favouriteEntity.country,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "delete",
                modifier = Modifier.clickable {
                    viewModel.deleteFavourite(favouriteEntity)
                },
                tint = Color.Red.copy(alpha = 0.6f)
            )
        }
    }
}

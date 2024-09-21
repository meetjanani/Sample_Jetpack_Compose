package com.example.sample_jetpack_compose.weatherApp.screens.mainScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sample_jetpack_compose.ui.theme.AppColors
import com.example.sample_jetpack_compose.weatherApp.data.DataOrExceptionWeatherApi
import com.example.sample_jetpack_compose.weatherApp.model.weatherApiModel.Weather
import com.example.sample_jetpack_compose.weatherApp.navigation.WeatherScreen
import com.example.sample_jetpack_compose.weatherApp.screens.settingScreen.SettingViewModel
import com.example.sample_jetpack_compose.weatherApp.utils.formatDate
import com.example.sample_jetpack_compose.weatherApp.utils.formatDecimals
import com.example.sample_jetpack_compose.weatherApp.widgets.WeatherStateImage
import com.example.sample_jetpack_compose.weatherApp.widgets.WeatherTopBar
import com.google.gson.Gson
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainWeatherScreen(
    navController: NavController,
    searchedCityName: String?,
    mainViewModel: MainViewModel = koinViewModel(),
    settingViewModel: SettingViewModel = koinViewModel()
) {
    val curCity: String = if(searchedCityName!!.isBlank()) "Surat" else searchedCityName
    val unitFromDb = settingViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }
    if(!unitFromDb.isNullOrEmpty()) {
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"


        val weatherData = produceState<DataOrExceptionWeatherApi<Weather, Boolean, Exception>>(
            initialValue = DataOrExceptionWeatherApi(loading = true)
        ) {
            value = mainViewModel.getWeatherData(city = curCity,
                units = unit)
        }.value

        if (weatherData.loading == true) {
            CircularProgressIndicator(progress = 0.89f)
        } else if (weatherData.data != null) {
            weatherData.data?.run {
                MainScaffold(this, navController, isImperial)
            }
        } else {
//        Text("ERROR")
            val weatherResponse = Gson().fromJson(
                "{\"city\":{\"coord\":{\"lat\":22.3,\"lon\":70.7833},\"country\":\"IN\",\"id\":1258847,\"name\":\"Rajkot\",\"population\":1177362,\"timezone\":19800},\"cnt\":7,\"cod\":\"200\",\"list\":[{\"clouds\":100,\"deg\":254,\"dt\":1722753000,\"feels_like\":{\"day\":96.01,\"eve\":95.68,\"morn\":78.93,\"night\":79.32},\"gust\":27.49,\"humidity\":70,\"pop\":1.0,\"pressure\":1003,\"rain\":2.95,\"speed\":18.9,\"sunrise\":1722732600,\"sunset\":1722779751,\"temp\":{\"day\":86.38,\"eve\":86.43,\"max\":87.73,\"min\":78.82,\"morn\":78.93,\"night\":79.32},\"weather\":[{\"description\":\"light rain\",\"icon\":\"10d\",\"id\":500,\"main\":\"Rain\"}]},{\"clouds\":100,\"deg\":234,\"dt\":1722839400,\"feels_like\":{\"day\":92.07,\"eve\":94.55,\"morn\":80.22,\"night\":79.11},\"gust\":25.64,\"humidity\":70,\"pop\":0.66,\"pressure\":1004,\"rain\":0.98,\"speed\":18.48,\"sunrise\":1722819025,\"sunset\":1722866116,\"temp\":{\"day\":84.72,\"eve\":85.96,\"max\":88.75,\"min\":78.4,\"morn\":78.44,\"night\":79.11},\"weather\":[{\"description\":\"light rain\",\"icon\":\"10d\",\"id\":500,\"main\":\"Rain\"}]},{\"clouds\":79,\"deg\":232,\"dt\":1722925800,\"feels_like\":{\"day\":94.82,\"eve\":94.19,\"morn\":79.54,\"night\":78.96},\"gust\":25.81,\"humidity\":63,\"pop\":1.0,\"pressure\":1005,\"rain\":0.85,\"speed\":19.42,\"sunrise\":1722905449,\"sunset\":1722952481,\"temp\":{\"day\":87.22,\"eve\":86.74,\"max\":89.4,\"min\":77.86,\"morn\":77.86,\"night\":78.96},\"weather\":[{\"description\":\"light rain\",\"icon\":\"10d\",\"id\":500,\"main\":\"Rain\"}]},{\"clouds\":86,\"deg\":244,\"dt\":1723012200,\"feels_like\":{\"day\":91.31,\"eve\":93.92,\"morn\":78.73,\"night\":79.41},\"gust\":28.07,\"humidity\":66,\"pop\":0.29,\"pressure\":1006,\"rain\":0.97,\"speed\":19.08,\"sunrise\":1722991873,\"sunset\":1723038844,\"temp\":{\"day\":85.03,\"eve\":86.81,\"max\":88.59,\"min\":78.26,\"morn\":78.73,\"night\":79.41},\"weather\":[{\"description\":\"light rain\",\"icon\":\"10d\",\"id\":500,\"main\":\"Rain\"}]},{\"clouds\":100,\"deg\":233,\"dt\":1723098600,\"feels_like\":{\"day\":87.1,\"eve\":91.62,\"morn\":79.72,\"night\":80.35},\"gust\":26.15,\"humidity\":77,\"pop\":0.89,\"pressure\":1005,\"rain\":1.23,\"speed\":20.24,\"sunrise\":1723078297,\"sunset\":1723125206,\"temp\":{\"day\":81.57,\"eve\":85.01,\"max\":86.95,\"min\":78.21,\"morn\":78.21,\"night\":78.69},\"weather\":[{\"description\":\"light rain\",\"icon\":\"10d\",\"id\":500,\"main\":\"Rain\"}]},{\"clouds\":49,\"deg\":234,\"dt\":1723185000,\"feels_like\":{\"day\":93.65,\"eve\":93.83,\"morn\":79.38,\"night\":78.96},\"gust\":25.81,\"humidity\":65,\"pop\":1.0,\"pressure\":1006,\"rain\":2.4,\"speed\":19.93,\"sunrise\":1723164721,\"sunset\":1723211568,\"temp\":{\"day\":86.31,\"eve\":86.97,\"max\":88.95,\"min\":77.68,\"morn\":77.68,\"night\":78.96},\"weather\":[{\"description\":\"light rain\",\"icon\":\"10d\",\"id\":500,\"main\":\"Rain\"}]},{\"clouds\":83,\"deg\":245,\"dt\":1723271400,\"feels_like\":{\"day\":93.11,\"eve\":92.3,\"morn\":79.7,\"night\":79.11},\"gust\":28.92,\"humidity\":63,\"pop\":1.0,\"pressure\":1006,\"rain\":1.95,\"speed\":21.88,\"sunrise\":1723251144,\"sunset\":1723297928,\"temp\":{\"day\":86.43,\"eve\":85.15,\"max\":88.03,\"min\":78.06,\"morn\":78.06,\"night\":79.11},\"weather\":[{\"description\":\"light rain\",\"icon\":\"10d\",\"id\":500,\"main\":\"Rain\"}]}],\"message\":3.3983745}",
                Weather::class.java
            )
            MainScaffold(
                weatherResponse, navController, isImperial
            )
        }
    } else {

    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScaffold(weather: Weather, navController: NavController,
                 isImperial: Boolean) {
    Scaffold(
        topBar = {
            WeatherTopBar(title = weather.city.name + ", ${weather.city.country}",
                navController = navController,
                isMainScreen = true,
//                icon = Icons.Default.ArrowBack,
                onButtonClicked = {
                    Log.d("", "")
                },
                onAddActionClicked = {
                    navController.navigate(WeatherScreen.SearchScreenRoute.name)
                })
        },
        modifier = Modifier.fillMaxWidth(),
    ) {
        MainContent(data = weather, isImperial, Modifier.padding(it))
    }
}

@Composable
fun MainContent(data: Weather, isImperial: Boolean, modifier: Modifier) {
    val imageUrl = "https://openweathermap.org/img/wn/${data?.list?.firstOrNull()?.weather?.firstOrNull()?.icon}.png"
    val weatherObject = data.list.first()
    Column(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = formatDate((weatherObject.dt ?: 0)),
            color = Color.Blue,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleLarge
        )

        Surface(modifier = Modifier
            .padding(4.dp)
            .size(200.dp),
            shape = CircleShape,
            color = AppColors.yellowWeatherTheme) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(text = formatDecimals(weatherObject.temp?.day?: 0.0) + "º", style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold)
                Text(text = weatherObject.weather?.firstOrNull()?.main ?: "", style = MaterialTheme.typography.bodySmall)
            }
        }
        HumidityWindPressureRow(weatherObject, isImperial)
        Divider()
        SunsetSunRiseRow(weatherObject)
        Text(
            text = "This Week", style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        ThisWeekTitleRow(data.list)
    }
}


fun <T> Boolean.customIfElse(ifValue: T, elseValue: T): T {
    return if (this) ifValue else elseValue
}
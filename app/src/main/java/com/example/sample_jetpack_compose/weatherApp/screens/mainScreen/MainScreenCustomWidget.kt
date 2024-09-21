package com.example.sample_jetpack_compose.weatherApp.screens.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.sample_jetpack_compose.R
import com.example.sample_jetpack_compose.ui.theme.AppColors
import com.example.sample_jetpack_compose.weatherApp.model.weatherApiModel.WeatherItem
import com.example.sample_jetpack_compose.weatherApp.utils.formatDate
import com.example.sample_jetpack_compose.weatherApp.utils.formatDateTime
import com.example.sample_jetpack_compose.weatherApp.utils.formatDecimals
import com.example.sample_jetpack_compose.weatherApp.widgets.WeatherStateImage

@Composable
fun HumidityWindPressureRow(weather: WeatherItem, isImperial: Boolean) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = " ${weather.humidity}%", style = MaterialTheme.typography.titleSmall
            )
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = " ${weather.pressure} psi", style = MaterialTheme.typography.titleSmall
            )
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = " ${weather.speed} " + if(isImperial) "mph" else "m/s", style = MaterialTheme.typography.titleSmall
            )
        }
    }
}


@Composable
fun SunsetSunRiseRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(top = 15.dp, bottom = 6.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Image(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "humidity icon",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = formatDateTime(weather.sunrise ?: 0),
                style = MaterialTheme.typography.titleSmall
            )
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Image(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "humidity icon",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = formatDateTime(weather.sunset ?: 0),
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
fun ThisWeekTitleRow(weatherList: List<WeatherItem>) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFD0BCFF),
        shape = RoundedCornerShape(size = 14.dp)
    ) {
        LazyColumn(
            modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(1.dp)
        ) {
            items(items = weatherList) {
                WeatherDetailRow(weather = it)
            }

        }

    }
}

@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather?.firstOrNull()?.icon}.png"

    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = AppColors.mOffWhite
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
//            formatDate(weather.dt?: 0).substring(0,3)
            Text(
                text = formatDate(weather.dt ?: 0).split(",")[0],
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                style = MaterialTheme.typography.titleSmall
            )
            WeatherStateImage(imageUrl = imageUrl)
            Surface(
                modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = AppColors.yellowWeatherTheme
            ) {
                Text(
                    text = weather.weather?.firstOrNull()?.description ?: "",
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.Blue.copy(alpha = 0.7f),
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append(formatDecimals((weather.temp?.max ?: 0.0)) + "º")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Color.LightGray,
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append(formatDecimals((weather.temp?.min ?: 0.0)) + "º")
                        }
                    },
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

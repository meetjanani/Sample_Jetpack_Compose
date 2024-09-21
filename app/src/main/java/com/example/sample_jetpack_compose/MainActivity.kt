package com.example.sample_jetpack_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sample_jetpack_compose.screens.QuestionDisplayScreen
import com.example.sample_jetpack_compose.ui.theme.Hello_Jetpack_ComposeTheme
import com.example.sample_jetpack_compose.weatherApp.navigation.WeatherNavigation

// API URL: https://randomuser.me/api/?page=1&results=1

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        WeatherAppApplication()
//            QuizApplication()
        }
    }
}



@Composable
fun QuizApplication() {
    Hello_Jetpack_ComposeTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            QuestionDisplayScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherAppApplication() {
    Hello_Jetpack_ComposeTheme {
        Surface(color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherNavigation()
            }
        }
    }
}
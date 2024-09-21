package com.example.sample_jetpack_compose.weatherApp.screens.settingScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sample_jetpack_compose.weatherApp.model.roomEntity.UnitEntity
import com.example.sample_jetpack_compose.weatherApp.widgets.WeatherTopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingScreen(
    navController: NavController, settingViewModel: SettingViewModel = koinViewModel()
) {
    var unitToggleState by remember {
        mutableStateOf(false)
    }
    val mesurementUnits = listOf("Imperial (F)", "Metric (C)")
    val choiceFromDb = settingViewModel.unitList.collectAsState().value
    val defaultChoice = if (choiceFromDb.isEmpty()) mesurementUnits[0]
    else choiceFromDb[0].unit
    var choiceState by remember {
        mutableStateOf(defaultChoice)
    }
    Scaffold(topBar = {
        WeatherTopBar(
            navController = navController,
            title = "Settings",
            icon = Icons.Default.ArrowBack,
            isMainScreen = false
        ) {
            navController.popBackStack()
        }
    }) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change Units of Mesurement", modifier = Modifier.padding(bottom = 15.dp)
                )

                IconToggleButton(
                    checked = !unitToggleState,
                    onCheckedChange = {
                        unitToggleState = !it
                        if (unitToggleState) {
                            choiceState = "Imperial (F)"
                        } else {
                            choiceState = "Metric (C)"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(Color.Magenta.copy(alpha = 0.4f))
                ) {
                    Text(text = if (unitToggleState) "Fahrenheit ºF" else "Celsius ºC")
                }

                Button(
                    onClick = {
                        settingViewModel.deleteAllUnits()
                        settingViewModel.insertUnit(UnitEntity(unit = choiceState))
                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEFBE42)
                    )
                ) {
                    Text(
                        "Save",
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
    Text(text = "Setting")
}
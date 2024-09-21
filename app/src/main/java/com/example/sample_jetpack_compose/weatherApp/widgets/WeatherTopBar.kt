package com.example.sample_jetpack_compose.weatherApp.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sample_jetpack_compose.ui.theme.AppColors
import com.example.sample_jetpack_compose.weatherApp.model.roomEntity.FavouriteEntity
import com.example.sample_jetpack_compose.weatherApp.navigation.WeatherScreen
import com.example.sample_jetpack_compose.weatherApp.screens.favouriteScreen.FavouriteViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopBar(
    title: String? = null,
    icon: ImageVector? = null,
    isMainScreen: Boolean = false,
    elevation: Dp = 0.dp,
    navController: NavController,
    favouriteViewModel: FavouriteViewModel = koinViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showDialog = remember {
        mutableStateOf(false)
    }
    val showIt = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController)
    } else {

    }

    TopAppBar(modifier = Modifier
        .fillMaxWidth()
        .background(Color.Red), title = {
        Text(
            text = title.orEmpty(),
            color = AppColors.mOffWhite,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 15.sp)
        )
    }, actions = {
        if (isMainScreen) {
            IconButton(onClick = {
                onAddActionClicked.invoke()
            }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search Icon",
                    tint = AppColors.mOffWhite
                )
            }
            IconButton(onClick = {
                showDialog.value = true
            }) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = "More Icon",
                    tint = AppColors.mOffWhite
                )
            }
        } else {
            Box {}
        }
    }, navigationIcon = {
        if (icon != null) {
            Icon(imageVector = icon,
                contentDescription = "navigationIcon",
                tint = AppColors.mOffWhite,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clickable {
                        onButtonClicked.invoke()
                    })
        }
        if (isMainScreen) {
            val cityName = title?.split(",")?.get(0) ?: ""
            val countryName = title?.split(",")?.get(1)?.substring(1) ?: ""
            val isAlreadyAdded = favouriteViewModel.favList.collectAsState().value.contains(
                FavouriteEntity(
                    cityName.orEmpty(), countryName.orEmpty()
                )
            )
            if (isAlreadyAdded == false) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite icon",
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .scale(0.9f)
                        .clickable {
                            favouriteViewModel
                                .insertFavourite(
                                    FavouriteEntity(
                                        city = cityName, country = countryName
                                    )
                                )
                                .run {
                                    showIt.value = true
                                }
                        },
                    tint = Color.Red.copy(alpha = 0.6f)
                )
            } else {
                showIt.value = false
                Box {}
            }
        }
        ShowToast(context = context, showIt)
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary
    )
    )
}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if (showIt.value) {
        Toast.makeText(context, "City added to favorites", Toast.LENGTH_LONG).show()
    }
}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded by remember {
        mutableStateOf(true)
    }
    val items = listOf("About", "Favorites", "Settings")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(align = Alignment.TopEnd)
            .absolutePadding(top = 5.dp, right = 20.dp)
    ) {
        DropdownMenu(expanded = expanded, onDismissRequest = {
            expanded = false
        }, modifier = Modifier
            .width(140.dp)
            .background(Color.White)) {
            items.forEachIndexed { index, menuName ->
                DropdownMenuItem(text = {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Icon(
                            imageVector = when (menuName) {
                                "About" -> Icons.Default.Info
                                "Favorites" -> Icons.Default.FavoriteBorder
                                "Settings" -> Icons.Default.Settings
                                else -> Icons.Default.Settings
                            },
                            contentDescription = null,
                            tint = Color.LightGray,
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        Text(text = menuName, modifier = Modifier.clickable {
                            navController.navigate(
                                when (menuName) {
                                    "About" -> WeatherScreen.AboutScreenRoute.name
                                    "Favorites" -> WeatherScreen.FavouriteScreenRoute.name
                                    "Settings" -> WeatherScreen.SettingsScreenRoute.name
                                    else -> WeatherScreen.SettingsScreenRoute.name
                                }
                            )
                        }, fontWeight = FontWeight.W300)
                    }
                }, onClick = {
                    expanded = false
                    showDialog.value = false
                })
            }
        }
    }

}

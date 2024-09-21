package com.example.sample_jetpack_compose.firebaseApp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sample_jetpack_compose.firebaseApp.navigation.FirebaseScreens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun TitleSection(modifier: Modifier = Modifier,
                 lable: String) {
    Surface(
        modifier = modifier.padding(start = 5.dp, top = 1.dp),
    ) {
        Column {
            Text(text = lable,
                fontSize = 19.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Left,
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirebaseAppBar(
    title: String,
    icon: ImageVector? = null,
    showProfile: Boolean = true,
    navController: NavController,
    onBackArrowClicked: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(showProfile) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription ="Login Icon",
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .scale(0.9f))
//                        Image(painter = painterResource(id = R.drawable.wind), contentDescription = "Icon")
                }
                if(icon != null) {
                    Icon(imageVector = icon, contentDescription = "Arrow Back",
                        tint = Color.Red.copy(alpha = 0.7f),
                        modifier = Modifier.clickable {
                            onBackArrowClicked.invoke()
                        })
                }
                Spacer(modifier = Modifier.width(50.dp))
                Text(text = title,
                    color = Color.Red.copy(alpha = 0.7f),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.weight(150f))
            }
        },
        actions = {
            if(showProfile) Row {
                IconButton(onClick = {
                    FirebaseAuth.getInstance().signOut().run {
                        navController.navigate(FirebaseScreens.LoginScreenFBApp.name)
                    }
                }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription ="Logout",
//                          tint = Color.Green.copy(alpha = 0.4f),
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .scale(0.9f))
                }
            } else Box(modifier = Modifier)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
    )
}

@Composable
fun FabContent(onTap: () -> Unit) {
    FloatingActionButton(
        onClick = {
            onTap()
        }, shape = RoundedCornerShape(50.dp),
        contentColor = Color(0xFF92CBDF)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add a Book",
            tint = Color.White
        )

    }

}
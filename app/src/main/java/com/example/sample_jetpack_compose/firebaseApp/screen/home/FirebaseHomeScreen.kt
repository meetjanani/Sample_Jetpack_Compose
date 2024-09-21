@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.sample_jetpack_compose.firebaseApp.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sample_jetpack_compose.compnent.ListBookCardLayout
import com.example.sample_jetpack_compose.firebaseApp.components.FabContent
import com.example.sample_jetpack_compose.firebaseApp.components.FirebaseAppBar
import com.example.sample_jetpack_compose.firebaseApp.components.TitleSection
import com.example.sample_jetpack_compose.firebaseApp.model.FirebaseBookDataClass
import com.example.sample_jetpack_compose.firebaseApp.navigation.FirebaseScreens
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FirebaseHomeScreen(
    navController: NavController, viewModel: FirebaseHomeScreenViewModel = koinViewModel()
) {
    Scaffold(topBar = {
        FirebaseAppBar(
            title = "A. Reader",
            navController = navController
        )
    }, floatingActionButton = {
        FabContent(onTap = {
            navController.navigate(FirebaseScreens.SearchScreenFBApp.name)
        })
    }) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()) {
            HomeContent(navController, viewModel)
        }
    }
}

@Composable
fun HomeContent(
    navController: NavController = NavController(LocalContext.current),
    viewModel: FirebaseHomeScreenViewModel
) {
    var listOfBooks = listOf<FirebaseBookDataClass>()
    val currentUser = FirebaseAuth.getInstance().currentUser

    if (!viewModel.data.value.data.isNullOrEmpty()) {
        listOfBooks = viewModel.data.value.data?.toList()?.filter { bookRecord ->
            bookRecord.userId == currentUser?.uid.toString()
        }?.toList() ?: arrayListOf()
    }

    val currentUserName = if(!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)
    } else {
        "N/A"
    }
    Column(
        modifier = Modifier.padding(2.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.align(alignment = Alignment.Start)
        ) {
            TitleSection(lable = "Your reading\n " + "activity right now..")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Profile",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(FirebaseScreens.StatsScreenFBApp.name)
                        }
                        .size(45.dp),
                    tint = MaterialTheme.colorScheme.secondary)
                Text(text = currentUserName.toString(),
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Red,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip)
                Divider(
                    thickness = 1.dp,
                    color = Color.Gray
                )
            }
        }

        val readingNowBooks = listOfBooks.filter { it.startedReading != null && it.finishedReading == null }
        ReadingRightNowArea(books = readingNowBooks, navController, viewModel)

        TitleSection(lable = "Reading List")

        val addedBooks = listOfBooks.filter { it.startedReading == null && it.finishedReading == null }

        if(viewModel.data.value.loading == true) {}
        BookListArea(listOfBooks = addedBooks, navController, viewModel)
    }
}

@Composable
fun BookListArea(
    listOfBooks: List<FirebaseBookDataClass>,
    navController: NavController,
    viewModel: FirebaseHomeScreenViewModel
) {
    HorizontalScrollableComponent(listOfBooks, viewModel) {onCardPressed ->
        navController.navigate(FirebaseScreens.UpdateScreenFBApp.name + "/${onCardPressed}")
    }
}

@Composable
fun HorizontalScrollableComponent(
    listOfBooks: List<FirebaseBookDataClass>,
    viewModel: FirebaseHomeScreenViewModel,
    onCardPressed: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(280.dp)
            .horizontalScroll(scrollState)
    )
    {
        if(viewModel.data.value.loading ==true) {
            LinearProgressIndicator(progress = 0.9f)
        } else {
            if(listOfBooks.isNullOrEmpty()) {
                Surface(
                    modifier = Modifier.padding(23.dp)
                ) {
                    Text(text = "No books found, Add a book",
                        style = TextStyle(
                            color = Color.Red.copy(alpha = 0.4f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                }
            } else {
                for(book in listOfBooks) {
                    ListBookCardLayout(book = book) {
                        onCardPressed.invoke(book.googleBookId.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun ReadingRightNowArea(
    books: List<FirebaseBookDataClass>,
    navController: NavController,
    viewModel: FirebaseHomeScreenViewModel
) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(280.dp)
            .horizontalScroll(scrollState)
    ) {
        if(viewModel.data.value.loading ==true) {
            LinearProgressIndicator(progress = 0.9f)
        } else {
            if(books.isNullOrEmpty()) {
                Surface(
                    modifier = Modifier.padding(23.dp)
                ) {
                    Text(text = "No books found, Add a book",
                        style = TextStyle(
                            color = Color.Red.copy(alpha = 0.4f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                }
            } else {
                for(book in books) {
                    ListBookCardLayout(book = book) {onCardPressed ->
                        navController.navigate(FirebaseScreens.UpdateScreenFBApp.name + "/${onCardPressed}")
                    }
                }
            }
        }
    }
}

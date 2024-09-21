package com.example.sample_jetpack_compose.firebaseApp.screen.stats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sample_jetpack_compose.firebaseApp.components.FirebaseAppBar
import com.example.sample_jetpack_compose.firebaseApp.model.FirebaseBookDataClass
import com.example.sample_jetpack_compose.firebaseApp.screen.home.FirebaseHomeScreenViewModel
import com.example.sample_jetpack_compose.firebaseApp.screen.search.BookRowLayoutFirebaseData
import com.example.sample_jetpack_compose.firebaseApp.screen.search.BookRowLayoutGoogleApi
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun FirebaseStatsScreen(
    navController: NavController,
    viewModel: FirebaseHomeScreenViewModel = koinViewModel()
) {

    var books: List<FirebaseBookDataClass> = emptyList()
    val currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold(topBar = {
        FirebaseAppBar(
            title = "Book Stats",
            navController = navController,
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            showProfile = false
        ) {
            navController.popBackStack()
        }
    }) {
        Surface(modifier = Modifier.padding(it)) {
            books =
                viewModel.data.value.data?.filter { it.userId == currentUser?.uid } ?: emptyList()

            Column {
                Row {
                    Box(modifier = Modifier
                        .size(45.dp)
                        .padding(2.dp)) {
                        Icon(imageVector = Icons.Sharp.Person, contentDescription = "icons")
                    }
                    // paul @ me.com
                    Text(text = "Hi, ${currentUser?.email.toString().split("@")[0].uppercase(Locale.getDefault())}")
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(5.dp)
                ) {
                    val readBookList: List<FirebaseBookDataClass> =
                        books.filter { (it.userId == currentUser?.uid) && (it.finishedReading != null) }

                    val readingBooks = books.filter { it.startedReading != null  && it.finishedReading == null}


                    Column(
                        modifier = Modifier.padding(start = 25.dp, top = 4.dp, bottom = 4.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(text = "Your Stats",
                            style = MaterialTheme.typography.headlineMedium)
                        Divider()
                        Text(text = "You`re reading ${readingBooks.size}",
                            style = MaterialTheme.typography.headlineMedium)
                        Text(text = "You`re read ${readBookList.size}",
                            style = MaterialTheme.typography.headlineMedium)
                    }
                }

                if(viewModel.data.value.loading == true) {
                    LinearProgressIndicator(progress = 0.9f)
                } else {
                    Divider()
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        // filter books by finished ones
                        val readBooks : List<FirebaseBookDataClass> = viewModel.data.value.data?.filter { it.finishedReading != null && it.userId == currentUser?.uid } ?: emptyList()

                        items(items = readBooks) {bookRecord ->
                            BookRowLayoutFirebaseData(book = bookRecord, navController = navController)

                        }
                    }
                }
            }
        }
    }
}
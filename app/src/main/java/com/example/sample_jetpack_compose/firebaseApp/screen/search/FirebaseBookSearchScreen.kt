package com.example.sample_jetpack_compose.firebaseApp.screen.search

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.sample_jetpack_compose.compnent.CustomInputField
import com.example.sample_jetpack_compose.firebaseApp.components.FirebaseAppBar
import com.example.sample_jetpack_compose.firebaseApp.model.BookResponseDataClass
import com.example.sample_jetpack_compose.firebaseApp.model.FirebaseBookDataClass
import com.example.sample_jetpack_compose.firebaseApp.navigation.FirebaseScreens
import com.example.sample_jetpack_compose.firebaseApp.utils.FirebaseConstants
import com.example.sample_jetpack_compose.weatherApp.utils.formatDate
import com.example.sample_jetpack_compose.weatherApp.utils.formatDateTime
import org.koin.androidx.compose.koinViewModel

@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FirebaseSearchScreen(
    navController: NavController = NavController(LocalContext.current),
    viewModel: FirebaseBookSearchViewModel = koinViewModel()
) {
    Scaffold(topBar = {
        FirebaseAppBar(title = "Search Books",
            icon = Icons.Default.ArrowBack,
            navController = navController,
            showProfile = false,
            onBackArrowClicked = {
                navController.popBackStack()
//                    navController.navigate(FirebaseScreens.HomeScreenFBApp.name)
            })
    }) {
        Surface(
            modifier = Modifier.padding(it)
        ) {
            Column {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp), viewModel = viewModel
                ) { searchQuery ->
                    // handle onSearch
                    viewModel.searchBooks(searchQuery)
                }
                Spacer(modifier = Modifier.height(13.dp))
                BookList(navController = navController, viewModel)
            }
        }

    }
}

@Composable
fun BookList(navController: NavController, viewModel: FirebaseBookSearchViewModel) {
//     val listOfBooksd = viewModel.listOfBoks.value.data ?: listOf()
    /*val listOfBooksds = viewModel.listOfBoks.observeForever {
        Log.d("ddd", "${viewModel.listOfBoks.value}")
    }*/

    val listOfBooks = viewModel.list

    if (listOfBooks.size == 0) {

        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            LinearProgressIndicator(progress = 0.89f)
            Text(text = "Loading...")
        }
//        CircularProgressIndicator(progress = 0.89f)
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)
        ) {
            items(items = listOfBooks) { book ->
                BookRowLayoutGoogleApi(book, navController)
            }
        }
    }
}

@Composable
fun BookRowLayoutGoogleApi(book: BookResponseDataClass.BookDetails, navController: NavController) {
    Card(modifier = Modifier
        .clickable {
            navController.navigate(FirebaseScreens.DetailScreenFBApp.name + "/${book.id}")
        }
        .fillMaxWidth()
        .height(100.dp)
        .padding(3.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(7.dp)) {
        Row(
            modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.Top
        ) {
            val bookImage = book?.volumeInfo?.imageLinks?.smallThumbnail
            val imageUrl = if(bookImage == null) {
                "https://images.rawpixel.com/image_png_400/cHJpdmF0ZS9sci9pbWFnZXMvd2Vic2l0ZS8yMDIzLTA4L3Jhd3BpeGVsb2ZmaWNlMV9jdXRlXzNkX2lsbHVzdHJhdGlvbl9vZl9hX3N0YWNrX29mX2Jvb2tzX2lzb2xhdF81MjhhNmU5Ni0zZjllLTRlOGQtYmEyNy1lZGU3OWU0NTg0YTAucG5n.png 400w"
            } else {
                book.volumeInfo.imageLinks.smallThumbnail
            }
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = "book image",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp)
            )

            Column {
                Text(
                    text = book.volumeInfo.title.toString(), overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Author: " + book.volumeInfo.authors.toString(),
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodySmall
                )
                if (!book.volumeInfo.publishedDate.isNullOrEmpty()) Text(
                    text = "Date: " + book.volumeInfo.publishedDate.toString(),
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = book.volumeInfo.categories?.toString() ?: "",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

    }
}


@Composable
fun BookRowLayoutFirebaseData(book: FirebaseBookDataClass, navController: NavController) {
    Card(modifier = Modifier
        .clickable {
            navController.navigate(FirebaseScreens.DetailScreenFBApp.name + "/${book.googleBookId}")
        }
        .fillMaxWidth()
        .height(100.dp)
        .padding(3.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(7.dp)) {
        Row(
            modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.Top
        ) {
            val bookImage = book?.photoUrl
            val imageUrl = if(bookImage == null) {
                "https://images.rawpixel.com/image_png_400/cHJpdmF0ZS9sci9pbWFnZXMvd2Vic2l0ZS8yMDIzLTA4L3Jhd3BpeGVsb2ZmaWNlMV9jdXRlXzNkX2lsbHVzdHJhdGlvbl9vZl9hX3N0YWNrX29mX2Jvb2tzX2lzb2xhdF81MjhhNmU5Ni0zZjllLTRlOGQtYmEyNy1lZGU3OWU0NTg0YTAucG5n.png 400w"
            } else {
                book.photoUrl
            }
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = "book image",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp)
            )

            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = book.title.toString(), overflow = TextOverflow.Ellipsis
                    )

                    if((book.rating?.toInt() ?: 0) >= 3) {
                        Spacer(modifier = Modifier.fillMaxWidth(0.8f))
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Great rating",
                            tint = Color.Green.copy(alpha = 0.5f)
                        )
                    } else {
                        Box {}
                    }
                }
                Text(
                    text = "Author: " + book.authors.toString(),
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Started: " + FirebaseConstants.formatDate(book.startedReading!!),
                    softWrap = true,
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Finished ${FirebaseConstants.formatDate(book.finishedReading!!)}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

    }
}

@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    viewModel: FirebaseBookSearchViewModel,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {},
) {
    Column() {
        val searchQueryState = rememberSaveable {
            mutableStateOf("")
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value) {
            searchQueryState.value.trim().isNotEmpty()
        }

        CustomInputField(valueState = searchQueryState,
            lableId = "Search",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            })
    }
}
package com.example.sample_jetpack_compose.firebaseApp.screen.details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.sample_jetpack_compose.compnent.RoundedButton
import com.example.sample_jetpack_compose.firebaseApp.components.FirebaseAppBar
import com.example.sample_jetpack_compose.firebaseApp.data.FirebaseResource
import com.example.sample_jetpack_compose.firebaseApp.model.BookResponseDataClass
import com.example.sample_jetpack_compose.firebaseApp.model.FirebaseBookDataClass
import com.example.sample_jetpack_compose.firebaseApp.navigation.FirebaseScreens
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.compose.koinViewModel

@SuppressLint("ProduceStateDoesNotAssignValue")
@Composable
fun FirebaseDetailsScreen(
    navController: NavController,
    viewModel: FirebaseDetailScreenViewModel = koinViewModel(),
    bookId: String
) {
    Scaffold(topBar = {
        FirebaseAppBar(
            title = "Book Details",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            showProfile = false,
            navController = navController
        ) {
            navController.navigate(FirebaseScreens.SearchScreenFBApp.name)
        }
    }) {
        Surface(modifier = Modifier
            .padding(it)
            .padding(3.dp)
            .fillMaxSize(),
            onClick = { /*TODO*/ }) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val bookInfo =
                    produceState<FirebaseResource<BookResponseDataClass.BookDetails?>>(initialValue = FirebaseResource.Loading()) {
                        value = viewModel.getBookInfo(bookId = bookId)
                    }.value

                if (bookInfo.data == null) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        LinearProgressIndicator(progress = 0.89f)
                        Text(text = "Loading...")
                    }
                } else {
                    ShowBookDetails(bookInfo, navController)
                }
            }
        }
    }
}

@Composable
fun ShowBookDetails(
    bookInfo: FirebaseResource<BookResponseDataClass.BookDetails?>, navController: NavController
) {
    val bookData = bookInfo.data?.volumeInfo
    val googleBookId = bookInfo.data?.id

    Card(
        modifier = Modifier.padding(34.dp),
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Image(
            painter = rememberImagePainter(data = bookData?.imageLinks?.thumbnail),
            contentDescription = "Book Image",
            modifier = Modifier
                .width(90.dp)
                .height(90.dp)
                .padding(1.dp)
        )
    }
    Text(
        text = bookData?.title.toString(),
        style = MaterialTheme.typography.titleLarge,
        overflow = TextOverflow.Ellipsis,
        maxLines = 19
    )
    Text(text = "Authors: ${bookData?.authors.toString()}")
    Text(text = "Page Count: ${bookData?.pageCount.toString()}")
    Text(
        text = "Categories: ${bookData?.categories.toString()}",
        style = MaterialTheme.typography.bodySmall,
        overflow = TextOverflow.Ellipsis,
        maxLines = 3
    )
    Text(
        text = "Published: ${bookData?.authors.toString()}",
        style = MaterialTheme.typography.bodySmall
    )
    Spacer(modifier = Modifier.height(5.dp))

    val cleanDescription = HtmlCompat.fromHtml(bookData!!.description,
        HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

    val localDimension = LocalContext.current.resources.displayMetrics
    Surface(
        modifier = Modifier
            .height(localDimension.heightPixels.dp.times(0.09f))
            .padding(4.dp),
        shape = RectangleShape,
        border = BorderStroke(1.dp, Color.DarkGray)
    ) {
        LazyColumn(
            modifier = Modifier.padding(3.dp)
        ) {
            item {
                Text(text = cleanDescription)
            }
        }
    }
    // Buttons
    Row(
        modifier = Modifier.padding(top = 6.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        RoundedButton(
            label = "Save",
            onPress = {
                // save this book to the firestore database
                val fbAuth = FirebaseAuth.getInstance()
                bookData?.run {
                    val book = FirebaseBookDataClass(
                        title = this.title,
                        authors = this.authors.toString(),
                        description = this.description,
                        categories = this.categories.toString(),
                        notes = "",
                        photoUrl = this.imageLinks.thumbnail,
                        publishedDate = this.publishedDate,
                        pageCount = this.pageCount.toString(),
                        rating = 0.0,
                        googleBookId = googleBookId,
                        userId = fbAuth.uid.toString(),
                    )
                    saveToFirebase(book, navController)
                }
            }
        )
        Spacer(modifier = Modifier.width(25.dp))
        RoundedButton(
            label = "Cancel",
            onPress = {
                navController.popBackStack()
            }
        )
    }
}

fun saveToFirebase(firebaseBookDataClass: FirebaseBookDataClass, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")

    if(firebaseBookDataClass.toString().isNotEmpty()) {
        dbCollection.add(firebaseBookDataClass)
            .addOnSuccessListener { documentRef ->
                val docId = documentRef.id
                dbCollection.document(docId)
                    .update(hashMapOf("id" to docId) as Map<String, Any>)
                    .addOnCompleteListener {task ->
                        if(task.isSuccessful) {
                            navController.popBackStack()
                        }
                    }
                    .addOnFailureListener {
                        Log.e("error", "Save to Firebase: ${it.message}")
                    }
            }
    } else {

    }
}

package com.example.sample_jetpack_compose.firebaseApp.screen.update

import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.sample_jetpack_compose.R
import com.example.sample_jetpack_compose.compnent.CustomInputField
import com.example.sample_jetpack_compose.compnent.RoundedButton
import com.example.sample_jetpack_compose.firebaseApp.components.FirebaseAppBar
import com.example.sample_jetpack_compose.firebaseApp.data.DataOrException
import com.example.sample_jetpack_compose.firebaseApp.model.FirebaseBookDataClass
import com.example.sample_jetpack_compose.firebaseApp.navigation.FirebaseScreens
import com.example.sample_jetpack_compose.firebaseApp.screen.home.FirebaseHomeScreenViewModel
import com.example.sample_jetpack_compose.firebaseApp.utils.FirebaseConstants
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.compose.koinViewModel

@Composable
fun FirebaseUpdateScreen(
    navController: NavController,
    viewModel: FirebaseHomeScreenViewModel = koinViewModel(),
    bookItemId: String
) {
    Scaffold(topBar = {
        FirebaseAppBar(
            title = "Update Book",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            showProfile = false,
            navController = navController
        ) {
            navController.popBackStack()
        }
    }) {
        val bookInfo =
            produceState<DataOrException<List<FirebaseBookDataClass>, Boolean, Exception>>(
                initialValue = DataOrException(data = emptyList(), true, Exception(""))
            ) {
                value = viewModel.data.value
            }.value

        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(3.dp)
        ) {
            Column(
                modifier = Modifier.padding(top = 3.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (bookInfo.loading == true) {
                    LinearProgressIndicator(progress = 0.9f)
                    bookInfo.loading = false
                } else {
                    Surface(
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth(),
                        shape = CircleShape,
                        tonalElevation = 4.dp,
                        shadowElevation = 4.dp,
                        color = Color.White
                    ) {
                        ShowBookUpdate(bookInfo = viewModel.data.value, bookItemId = bookItemId)
                    }
                    // update note edit text
                    ShowUpdateBookForm(book = viewModel.data.value.data?.first { bookRecord ->
                        bookRecord.googleBookId == bookItemId
                    }, navController)

                }
            }
        }

    }

}

@Composable
fun ShowUpdateBookForm(book: FirebaseBookDataClass?, navController: NavController) {

    val context = LocalContext.current
    val notesText = remember {
        mutableStateOf("")
    }
    val isStartedReading = remember {
        mutableStateOf(false)
    }
    val isFinishedReading = remember {
        mutableStateOf(false)
    }
    val ratingVal = remember {
        mutableStateOf(0)
    }
    SimpleForm(defaultValue = book?.notes.toString()
        .ifEmpty { "No thoughts available" }) { updatedNote ->
        notesText.value = updatedNote
    }

    // Start & Finished Reading buttons
    Row(
        modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        TextButton(
            onClick = {
                isStartedReading.value = true
            }, enabled = book?.startedReading == null
        ) {
            if (book?.startedReading == null) {
                if (!isStartedReading.value) {
                    Text(text = "Started Reading")
                } else {
                    Text(
                        text = "Started Reading!",
                        modifier = Modifier.alpha(0.6f),
                        color = Color.Red.copy(alpha = 0.5f)
                    )
                }
            } else {
                Text(text = "Started on : ${FirebaseConstants.formatDate(book.startedReading!!)}")
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        TextButton(
            onClick = {
                isFinishedReading.value = true
            },
            enabled = book?.finishedReading == null,
        ) {
            if (book?.finishedReading == null) {
                if (!isFinishedReading.value) {
                    Text(text = "Mark as Read")
                } else {
                    Text(text = "Finished Reading!")
                }
            } else {
                Text(text = "Finished on: ${FirebaseConstants.formatDate(book.finishedReading!!)}")
            }

        }
    }

    // Rating
    Text(text = "Rating View")
    book?.rating?.toInt()?.let { originalRating ->
        CustomRatingBar(
            rating = originalRating,
        ) { onPressRating ->
            ratingVal.value = onPressRating
        }
    }

    Spacer(modifier = Modifier.padding(15.dp))

    // Update & Delete Buttons
    Row {
        RoundedButton(
            label = "Update",
        ) {
            val changedNote = book?.notes != notesText.value
            val changedRating = book?.rating?.toInt() != ratingVal.value
            val isFinishedTimeStamp =
                if (isFinishedReading.value) Timestamp.now() else book?.finishedReading
            val isStartedTimeStamp =
                if (isStartedReading.value) Timestamp.now() else book?.startedReading
            val isBookUpdateRequired =
                changedNote || changedRating || isStartedReading.value || isFinishedReading.value
            val bookToUpdate = hashMapOf(
                "finished_reading" to isFinishedTimeStamp,
                "started_reading" to isStartedTimeStamp,
                "rating" to ratingVal.value,
                "notes" to notesText.value,
            ).toMap()

            if (isBookUpdateRequired) {
                FirebaseFirestore.getInstance().collection("books").document(book?.id!!)
                    .update(bookToUpdate).addOnCompleteListener { task ->
                        Toast.makeText(context, "Book Updated Successfully!", Toast.LENGTH_LONG)
                            .show()
                        navController.navigate(FirebaseScreens.HomeScreenFBApp.name)

                    }.addOnFailureListener {
                        Log.d("Error", "${it.message}")
                    }
            } else {
                // No any change made by user
            }
        }
        Spacer(modifier = Modifier.width(100.dp))

        val openDialog = remember {
            mutableStateOf(false)
        }
        ShowAlertDialog(title = context.getString(R.string.are_you_sure_warning_message) + "\n" + context.getString(
            R.string.are_you_sure_warning_message
        ), openDialog, onYesPressed = {
            FirebaseFirestore.getInstance().collection("books").document(book?.id!!).delete()
                .addOnCompleteListener {
                    openDialog.value = false/*
                    * popBackStack() will not do immediate recomposition
                    * where in navigate do the immediate recomposition
                    * chose wisely
                    * */
                    navController.navigate(FirebaseScreens.HomeScreenFBApp.name)
                }.addOnFailureListener { }
        })

        RoundedButton(label = "Delete", onPress = {
            openDialog.value = true

        })
    }
}

@Composable
fun ShowAlertDialog(
    title: String, openDialog: MutableState<Boolean>, onYesPressed: () -> Unit
) {
    if (openDialog.value) {
        AlertDialog(onDismissRequest = {
            openDialog.value = false
        }, title = {
            Text(text = "Delete Book")
        }, text = {
            Text(
                text = title
            )
        }, confirmButton = {
            TextButton(onClick = {
                onYesPressed.invoke()
            }) {
                Text(text = "Yes")
            }
        })
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomRatingBar(
    modifier: Modifier = Modifier, rating: Int, onPressRating: (Int) -> Unit
) {
    var ratingState by remember {
        mutableStateOf(rating)
    }
    var selected by remember {
        mutableStateOf(false)
    }
    val size by animateDpAsState(
        targetValue = if (selected) 42.dp else 34.dp, spring(Spring.DampingRatioMediumBouncy)
    )

    Row(
        modifier = Modifier.width(280.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star Rating",
                modifier = modifier
                    .width(size)
                    .height(size)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                selected = true
                                onPressRating(i)
                                ratingState = i
                            }

                            MotionEvent.ACTION_UP -> {
                                selected = false
                            }
                        }
                        true
                    },
                tint = if (i <= ratingState) Color(0xFFFFD700) else Color(0xFFA2ADB1)
            )
        }
    }
}

@Composable
fun SimpleForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    defaultValue: String = "Great Bok!",
    onSearch: (String) -> Unit
) {
    Column {
        val textFieldValue = rememberSaveable {
            mutableStateOf(defaultValue)
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(textFieldValue.value) {
            textFieldValue.value.trim().isNotBlank()
        }
        CustomInputField(modifier = modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(3.dp)
            .background(Color.White, CircleShape)
            .padding(horizontal = 20.dp, vertical = 12.dp),
            valueState = textFieldValue,
            lableId = "Enter Your thought",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(textFieldValue.value.trim())
                keyboardController?.hide()
            })
    }
}

@Composable
fun ShowBookUpdate(
    bookInfo: DataOrException<List<FirebaseBookDataClass>, Boolean, Exception>, bookItemId: String
) {
    Row {
        Spacer(modifier = Modifier.width(43.dp))
        if (bookInfo.data != null) {
            Column(
                modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.Center
            ) {
                CardListItem(book = bookInfo.data!!.first { bookRecord ->
                    bookRecord.googleBookId == bookItemId
                }, onPressDetails = {

                })
            }
        }
    }

}

@Composable
fun CardListItem(book: FirebaseBookDataClass?, onPressDetails: () -> Unit) {

    Card(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable {

            },
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = rememberImagePainter(data = book?.photoUrl.toString()),
                contentDescription = "Book Image",
                modifier = Modifier
                    .height(100.dp)
                    .width(120.dp)
                    .padding(4.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 120.dp, topEnd = 20.dp, bottomEnd = 0.dp, bottomStart = 0.dp
                        )
                    )
            )
            Column(
                modifier = Modifier
            ) {
                Text(
                    text = book?.title.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .width(120.dp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = book?.authors.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 0.dp)
                        .width(120.dp)
                )
                Text(
                    text = book?.publishedDate.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                        .width(120.dp)
                )
            }
        }
    }
}

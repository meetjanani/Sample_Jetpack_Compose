package com.example.sample_jetpack_compose.firebaseApp.utils

import android.icu.text.DateFormat
import com.google.firebase.Timestamp

object FirebaseConstants {
    // https://www.googleapis.com/books/v1/volumes?q=flutter
    const val BASE_URL = "https://www.googleapis.com/books/v1/"

    fun formatDate(timeStamp: Timestamp): String {
        return DateFormat.getDateInstance()
            .format(timeStamp.toDate())
            .toString()
            .split(",")[0] // Dec 12, 2024
    }
}
package com.example.sample_jetpack_compose.firebaseApp.data

sealed class FirebaseResource<T>(
    val data: T? = null, val message: String? = null
) {
    class Success<T>(data: T) : FirebaseResource<T>(data)
    class Error<T>(message: String?, data: T? = null) : FirebaseResource<T>(data, message)
    class Loading<T>(data: T? = null) : FirebaseResource<T>(data)
}
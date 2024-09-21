package com.example.sample_jetpack_compose.firebaseApp.screen.login

import androidx.loader.content.Loader

data class LoadingState(
    val status: Status,
    val message: String? = null,
) {
    companion object {
        val IDLE = LoadingState(Status.IDLE)
        val SUCCESS = LoadingState(Status.SUCCESS)
        val LOADING = LoadingState(Status.LOADING)
        val FAILED = LoadingState(Status.FAILED)
    }
    enum class Status {
        SUCCESS,
        FAILED,
        LOADING,
        IDLE
    }
}

package com.example.sample_jetpack_compose.network

import com.example.sample_jetpack_compose.model.Question
import retrofit2.http.GET

interface QuestionApi {

    @GET("world.json")
    suspend fun getAllQuestions() : Question
}
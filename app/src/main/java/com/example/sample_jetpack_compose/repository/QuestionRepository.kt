package com.example.sample_jetpack_compose.repository

import android.util.Log
import com.example.sample_jetpack_compose.data.DataOrException
import com.example.sample_jetpack_compose.model.QuestionItem
import com.example.sample_jetpack_compose.network.QuestionApi

typealias Exception1 = Exception

class QuestionRepository(private val api: QuestionApi) {
    private val dataOrException = DataOrException<ArrayList<QuestionItem>,
            Boolean,
            Exception1>()

    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()
            if (dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false

        } catch (exception: Exception) {
            dataOrException.e = exception
            Log.e("", "getAllQuestions: ${dataOrException.e?.localizedMessage}")
        }
        return dataOrException
    }
}
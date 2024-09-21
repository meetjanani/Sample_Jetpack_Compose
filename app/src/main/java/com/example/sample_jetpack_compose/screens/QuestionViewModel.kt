package com.example.sample_jetpack_compose.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample_jetpack_compose.data.DataOrException
import com.example.sample_jetpack_compose.model.QuestionItem
import com.example.sample_jetpack_compose.repository.QuestionRepository
import kotlinx.coroutines.launch

class QuestionViewModel(private val repository: QuestionRepository) :
    ViewModel() {
     val data: MutableState<DataOrException<ArrayList<QuestionItem>, Boolean, Exception>> =
        mutableStateOf(
            DataOrException(null, true, Exception(""))
        )

    init {
        getAllQuestions()
    }

    private fun getAllQuestions() {
        viewModelScope.launch {
            data.value.run {
                loading = true
                repository.getAllQuestions()
                if(data.toString().isNotEmpty()) {
                    loading = false
                }
            }
            // ^^   Improved version using kotlin scope functions.
            data.value.loading = true
            data.value = repository.getAllQuestions()
            if(data.value.data.toString().isNotEmpty()) {
                data.value.loading = false
            }
        }
    }
}
package com.example.sample_jetpack_compose.firebaseApp.screen.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample_jetpack_compose.firebaseApp.data.DataOrException
import com.example.sample_jetpack_compose.firebaseApp.model.FirebaseBookDataClass
import com.example.sample_jetpack_compose.firebaseApp.repository.FirebaseRepository
import kotlinx.coroutines.launch

class FirebaseHomeScreenViewModel(
    private val repository: FirebaseRepository
): ViewModel() {

    val data: MutableState<DataOrException<List<FirebaseBookDataClass>, Boolean, Exception>>
    = mutableStateOf(DataOrException(listOf(), true, Exception("")))

    init {
        getAllFirebaseBooksFromDatabase()
    }

    private fun getAllFirebaseBooksFromDatabase() {
        viewModelScope.launch {
            data.value.run {
                this.loading = true
                this.data = repository.getAllFirebaseBooksFromDatabase().data
                this.loading = false
            }
            Log.d("Result", "getAllFirebaseBooksFromDatabase ${data.value.data?.toList().toString()}")
        }
    }
}
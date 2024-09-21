package com.example.sample_jetpack_compose.firebaseApp.screen.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample_jetpack_compose.firebaseApp.data.FirebaseResource
import com.example.sample_jetpack_compose.firebaseApp.model.BookResponseDataClass
import com.example.sample_jetpack_compose.firebaseApp.repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirebaseBookSearchViewModel(val repository: BookRepository) : ViewModel() {
    /* var listOfBoks: MutableLiveData<DataOrException<List<BookResponseDataClass.BookDetails>, Boolean, Exception>> =
         MutableLiveData(DataOrException(null, true, Exception("")))*/
    var list: ArrayList<BookResponseDataClass.BookDetails> by mutableStateOf(arrayListOf())
    var isLoading: Boolean by mutableStateOf(false)

    init {
        searchBooks("android")
    }


    fun searchBooks(query: String) {
        isLoading = true
        viewModelScope.launch {
            if (query.isEmpty()) return@launch
            try {
                list.clear()
                list = list
                when (val resposne = repository.getBooks(query)) {
                    is FirebaseResource.Success -> {
                        list = (resposne.data as ArrayList<BookResponseDataClass.BookDetails>?) ?: arrayListOf()
                        if (list.isNotEmpty()) isLoading = false
                    }

                    is FirebaseResource.Error -> {
                        isLoading = false
                        Log.d("Error", "Error")
                    }

                    is FirebaseResource.Loading -> {
                        isLoading = true
                        Log.d("Loading", "loading")
                    }
                    else -> {
                        isLoading = true
                    }
                }
            } catch (e: Exception) {
                Log.d("Error", e.message.toString())
            }/*listOfBoks.value = repository.getBooks(query)
                this@apply.data = repository.getBooks(query).data
                Log.d("data", "${listOfBoks.value?.data?.size}")
                if(this@apply.data?.isNotEmpty() == true) {
                    this@apply.loading = false
                }*/
        }
    }


}
package com.example.sample_jetpack_compose.firebaseApp.screen.details

import androidx.lifecycle.ViewModel
import com.example.sample_jetpack_compose.firebaseApp.data.FirebaseResource
import com.example.sample_jetpack_compose.firebaseApp.model.BookResponseDataClass
import com.example.sample_jetpack_compose.firebaseApp.repository.BookRepository

class FirebaseDetailScreenViewModel(
   val repository: BookRepository
) : ViewModel(){

    suspend fun getBookInfo(bookId: String): FirebaseResource<BookResponseDataClass.BookDetails?> {
        return repository.getBookInfoById(bookId)
    }
}
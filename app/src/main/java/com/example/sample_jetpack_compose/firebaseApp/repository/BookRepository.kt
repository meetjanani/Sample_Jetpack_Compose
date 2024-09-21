package com.example.sample_jetpack_compose.firebaseApp.repository

import com.example.sample_jetpack_compose.firebaseApp.data.DataOrException
import com.example.sample_jetpack_compose.firebaseApp.data.FirebaseResource
import com.example.sample_jetpack_compose.firebaseApp.model.BookResponseDataClass
import com.example.sample_jetpack_compose.firebaseApp.network.GoogleBooksApi

class BookRepository(private val api: GoogleBooksApi) {

    suspend fun getBooks(searchQuery: String): FirebaseResource<List<BookResponseDataClass.BookDetails>> {
        return try{
            FirebaseResource.Loading(data = true)
            val bookList = api.getAllBooks(searchQuery).items
            if(bookList.isNotEmpty()) {
                FirebaseResource.Loading(data = false)
            }
            FirebaseResource.Success(data = bookList)
        } catch (e: Exception) {
            FirebaseResource.Error(message = e.message.toString())
        }
    }

    suspend fun getBookInfoById(bookId: String): FirebaseResource<BookResponseDataClass.BookDetails?> {
        val response =  try {
            FirebaseResource.Loading(data = true)
            api.getBookInfoById(bookId) // it returns BookResponseDataClass.BookDetails
        } catch (e: Exception) {
            return FirebaseResource.Error(message = e.message.toString())
        }
        FirebaseResource.Loading(data = false)
        return FirebaseResource.Success(data = response)
    }
}
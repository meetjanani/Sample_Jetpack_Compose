package com.example.sample_jetpack_compose.firebaseApp.repository

import com.example.sample_jetpack_compose.firebaseApp.data.DataOrException
import com.example.sample_jetpack_compose.firebaseApp.model.BookResponseDataClass
import com.example.sample_jetpack_compose.firebaseApp.network.GoogleBooksApi

class SearchBookRepository(private val api: GoogleBooksApi) {

    private val getAllBooks =
        DataOrException<List<BookResponseDataClass.BookDetails>, Boolean, Exception>()
    private val getBookById =
        DataOrException<BookResponseDataClass.BookDetails, Boolean, Exception>()

    suspend fun getBooks(searchQuery: String): DataOrException<List<BookResponseDataClass.BookDetails>, Boolean, Exception> {
        try {
            getAllBooks.loading = true
            getAllBooks.data = api.getAllBooks(searchQuery).items
            if (getAllBooks.data?.isNotEmpty() == true) {
                getAllBooks.loading = false
            }
        } catch (e: Exception) {
            getAllBooks.e = e
        }
        return getAllBooks
    }

    suspend fun getBookInfoById(bookId: String): DataOrException<BookResponseDataClass.BookDetails, Boolean, Exception> {
        try {
            getBookById.loading = true
            getBookById.data = api.getBookInfoById(bookId)
            if (getBookById.data.toString().isNotEmpty()) {
                getBookById.loading = false
            }
        } catch (e: Exception) {
            getBookById.e = e
        }
        return getBookById
    }
}
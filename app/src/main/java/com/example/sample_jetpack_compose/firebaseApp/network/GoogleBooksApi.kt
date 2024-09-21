package com.example.sample_jetpack_compose.firebaseApp.network

import com.example.sample_jetpack_compose.firebaseApp.model.BookResponseDataClass
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoogleBooksApi {

    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String) : BookResponseDataClass

    @GET("volumes/{bookId}")
    suspend fun getBookInfoById(@Path("bookId") bookId: String): BookResponseDataClass.BookDetails
}
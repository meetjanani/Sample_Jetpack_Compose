package com.example.sample_jetpack_compose.firebaseApp.di

import com.example.sample_jetpack_compose.firebaseApp.network.GoogleBooksApi
import com.example.sample_jetpack_compose.firebaseApp.repository.BookRepository
import com.example.sample_jetpack_compose.firebaseApp.repository.FirebaseRepository
import com.example.sample_jetpack_compose.firebaseApp.repository.SearchBookRepository
import com.example.sample_jetpack_compose.firebaseApp.screen.details.FirebaseDetailScreenViewModel
import com.example.sample_jetpack_compose.firebaseApp.screen.home.FirebaseHomeScreenViewModel
import com.example.sample_jetpack_compose.firebaseApp.screen.login.FirebaseLoginScreenViewModel
import com.example.sample_jetpack_compose.firebaseApp.screen.search.FirebaseBookSearchViewModel
import com.example.sample_jetpack_compose.firebaseApp.utils.FirebaseConstants
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val firebaseAppDIModule  = module{
    viewModel<FirebaseLoginScreenViewModel>()
    viewModel<FirebaseHomeScreenViewModel>()
    viewModel<FirebaseBookSearchViewModel>()
    viewModel<FirebaseDetailScreenViewModel>()

    single { Retrofit.Builder()
        .baseUrl(FirebaseConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GoogleBooksApi::class.java) }
    single { SearchBookRepository(get()) } // get() supply GoogleBooksApi
    single { BookRepository(get()) } // get() supply GoogleBooksApi
    single { FirebaseRepository() }
//    single { FirebaseFirestore.getInstance().collection("books") } // Fire store book collection

}
package com.example.sample_jetpack_compose

import android.app.Application
import com.example.sample_jetpack_compose.firebaseApp.di.firebaseAppDIModule
import com.example.sample_jetpack_compose.network.QuestionApi
import com.example.sample_jetpack_compose.repository.QuestionRepository
import com.example.sample_jetpack_compose.screens.QuestionViewModel
import com.example.sample_jetpack_compose.util.Constants
import com.example.sample_jetpack_compose.weatherApp.di.weatherAppDIModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TriviaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TriviaApplication)
            modules(
                module {
                    // Quiz application
                    factory { QuestionRepository(get()) }
                    viewModel { QuestionViewModel(get()) }
                    single { Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(QuestionApi::class.java) }

                    // Weather application
                },
                weatherAppDIModule,
                firebaseAppDIModule
            )
        }
    }
}

// 4b174a9bc91e61d732d0021c56201d89
// https://api.openweathermap.org/data/2.5/forecast/daily?q=lisbon&appid=ed60fcfbd110ee65c7150605ea8aceea&units=imperial
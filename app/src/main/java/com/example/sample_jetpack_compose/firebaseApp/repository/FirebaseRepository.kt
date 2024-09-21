package com.example.sample_jetpack_compose.firebaseApp.repository

import com.example.sample_jetpack_compose.firebaseApp.data.DataOrException
import com.example.sample_jetpack_compose.firebaseApp.model.FirebaseBookDataClass
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class FirebaseRepository(val queryBook: Query = FirebaseFirestore.getInstance().collection("books")) {

    suspend fun getAllFirebaseBooksFromDatabase(): DataOrException<List<FirebaseBookDataClass>, Boolean, Exception> {
        val dataOrException = DataOrException<List<FirebaseBookDataClass>, Boolean, Exception>()

        dataOrException.run {
            try {
                loading = true
                data = queryBook.get().await().documents.map { documentSnapshot ->
                    documentSnapshot.toObject(FirebaseBookDataClass::class.java)!!
                }
                if (data.isNullOrEmpty()) loading = false
            } catch (exception: FirebaseFirestoreException) {
                e = exception
            }
        }
        return dataOrException

    }
}
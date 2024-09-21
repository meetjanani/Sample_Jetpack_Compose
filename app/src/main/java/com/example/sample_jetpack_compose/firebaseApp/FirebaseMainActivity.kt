package com.example.sample_jetpack_compose.firebaseApp

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sample_jetpack_compose.firebaseApp.navigation.FirebaseNavigation
import com.example.sample_jetpack_compose.ui.theme.Hello_Jetpack_ComposeTheme
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseMainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Hello_Jetpack_ComposeTheme {
               val db = FirebaseFirestore.getInstance()
               val user: MutableMap<String, Any> = HashMap()
                user["firstName"] = "Mohit"
                user["lastName"] = "Bajpai"

                /*db.collection("users").add(user)
                    .addOnSuccessListener {
                        Log.d("FB", "onCreate ${it.id}")
                    }.addOnFailureListener {
                        Log.d("FB", "onCreate ${it}")
                    }*/

                FirebaseApp()
            }
        }
    }

    @Composable
    fun FirebaseApp() {
        Surface(color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FirebaseNavigation()
            }
        }
    }
}
package com.example.sample_jetpack_compose.firebaseApp.screen.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample_jetpack_compose.firebaseApp.model.FirebaseUserDataClass
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class FirebaseLoginScreenViewModel : ViewModel() {
    //    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    fun signInWithEmailAndPassword(email: String, password: String, navigationBlock: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navigationBlock()
                    } else {
                        Log.d("Login Issue", "${task.result}")
                    }
                }
            } catch (ex: Exception) {
                Log.d("Login Error", "signInWithEmailAndPassword ${ex.message}")
            }
        }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        navigationBlock: () -> Unit
    ) = viewModelScope.launch {
        try {
            // logic
            if (_loading.value == false) {
                _loading.value = true
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val displayName = task.result.user?.email?.split("@")?.get(0)
                        createUser(displayName)
                        navigationBlock()
                    } else {
                        Log.d("SignUp Issue", "createUserWithEmailAndPassword ${task.result}")
                    }
                    _loading.value = false
                }
            }
        } catch (ex: Exception) {
            Log.d("SignUp Error", "signInWithEmailAndPassword ${ex.message}")
        }
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = FirebaseUserDataClass(userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "Life is great",
            profession = "Android Developer",
            id = null).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
    }
}
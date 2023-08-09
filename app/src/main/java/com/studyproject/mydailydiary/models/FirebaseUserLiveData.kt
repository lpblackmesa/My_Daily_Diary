package com.studyproject.mydailydiary.models

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseUserLiveData : LiveData<FirebaseUser?>() {
    //точкa входа в Firebase Authentication SDK.
    private val firebaseAuth = FirebaseAuth.getInstance()

    //слушатель состояния аутентификации пользователя, который будет получать текущего пользователя
    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        value = firebaseAuth.currentUser
    }

    //будет вызван, когда у LiveData появится хотя бы один подписчик
    override fun onActive() {
        firebaseAuth.addAuthStateListener(authStateListener)
    }
//когда все подписчики отпишутся
    override fun onInactive() {
        firebaseAuth.removeAuthStateListener(authStateListener)
    }
}

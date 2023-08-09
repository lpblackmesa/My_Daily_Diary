package com.studyproject.mydailydiary.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class LoginViewModel : ViewModel() {

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

//    authenticationState будет получать и хранить состояние аутентификации пользователя при любых изменениях этого состояния.
//    С помощью этой переменной классы фрагментов будут получать информацию о том, вошел ли пользователь в систему.

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }
}
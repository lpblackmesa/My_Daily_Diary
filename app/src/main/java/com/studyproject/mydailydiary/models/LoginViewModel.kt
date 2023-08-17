package com.studyproject.mydailydiary.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.studyproject.mydailydiary.data.AuthenticationStateEnum

class LoginViewModel : ViewModel() {


//    authenticationState будет получать и хранить состояние аутентификации пользователя при любых изменениях этого состояния.
//    С помощью этой переменной классы фрагментов будут получать информацию о том, вошел ли пользователь в систему.

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationStateEnum.AUTHENTICATED
        } else {
            AuthenticationStateEnum.UNAUTHENTICATED
        }
    }
}
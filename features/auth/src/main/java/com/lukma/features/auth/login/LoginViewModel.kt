package com.lukma.features.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lukma.core.domain.EventState
import com.lukma.core.domain.auth.usecase.SignInWithEmailUseCase
import com.lukma.core.domain.eventState
import org.koin.core.KoinComponent
import org.koin.core.inject

class LoginViewModel : ViewModel(), KoinComponent {
    private val signInWithEmailUseCase: SignInWithEmailUseCase by inject()

    private val authResultMutable = MutableLiveData<EventState<Unit>>()
    internal val authResult: LiveData<EventState<Unit>> = authResultMutable

    suspend fun signInWithEmail(email: String, password: String) {
        authResultMutable.value = EventState.Loading
        val result = signInWithEmailUseCase
            .addParams(email, password)
            .invoke()
            .eventState
        authResultMutable.postValue(result)
    }
}

package com.lukma.features.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lukma.core.domain.EventState
import com.lukma.core.domain.auth.usecase.SignInWithEmailUseCase
import com.lukma.core.domain.auth.usecase.SignUpWithEmailUseCase
import com.lukma.core.domain.eventState
import com.lukma.core.domain.isSuccess
import org.koin.core.KoinComponent
import org.koin.core.inject

class RegisterViewModel : ViewModel(), KoinComponent {
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase by inject()
    private val signInWithEmailUseCase: SignInWithEmailUseCase by inject()

    private val authResultMutable = MutableLiveData<EventState<Unit>>()
    internal val authResult: LiveData<EventState<Unit>> = authResultMutable

    suspend fun signUpWithEmail(email: String, password: String, displayName: String) {
        authResultMutable.value = EventState.Loading
        val result = signUpWithEmailUseCase
            .addParams(email, password, displayName)
            .invoke()
            .let {
                if (it.isSuccess) {
                    signInWithEmailUseCase
                        .addParams(email, password)
                        .invoke()
                } else {
                    it
                }
            }
            .eventState

        authResultMutable.postValue(result)
    }
}

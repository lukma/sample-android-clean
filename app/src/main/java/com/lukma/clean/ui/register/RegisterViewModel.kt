package com.lukma.clean.ui.register

import androidx.lifecycle.ViewModel
import com.lukma.clean.domain.auth.interactor.Register
import com.lukma.clean.ui.common.SingleLiveData

class RegisterViewModel(private val registerUseCase: Register) : ViewModel() {
    internal val registerLiveData = SingleLiveData(registerUseCase::execute)

    fun register(username: String, password: String, fullName: String, email: String) {
        registerLiveData.run(Register.Params(username, password, fullName, email))
    }

    override fun onCleared() {
        registerUseCase.dispose()
    }
}
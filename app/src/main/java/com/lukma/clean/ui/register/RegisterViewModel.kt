package com.lukma.clean.ui.register

import androidx.lifecycle.ViewModel
import com.lukma.clean.domain.auth.interactor.Register
import com.lukma.clean.domain.common.UseCaseConstant
import com.lukma.clean.ui.common.SingleLiveData

class RegisterViewModel(registerUseCase: Register) : ViewModel() {
    internal val registerLiveData = SingleLiveData(registerUseCase::execute)

    fun register(username: String, password: String, fullName: String, email: String) {
        registerLiveData.run(mapOf(
            UseCaseConstant.USERNAME to username,
            UseCaseConstant.PASSWORD to password,
            UseCaseConstant.FULLNAME to fullName,
            UseCaseConstant.EMAIL to email
        ))
    }
}
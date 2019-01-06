package com.lukma.clean.ui.register

import com.lukma.clean.domain.auth.interactor.RegisterUseCase
import com.lukma.clean.domain.common.UseCaseConstant
import com.lukma.clean.ui.common.ResourceLiveData
import com.lukma.clean.ui.common.base.BaseViewModel

class RegisterViewModel(registerUseCase: RegisterUseCase) : BaseViewModel() {
    internal val registerLiveData = ResourceLiveData<Unit>(registerUseCase)

    fun register(username: String, password: String, fullName: String, email: String) {
        registerLiveData.execute(mapOf(
            UseCaseConstant.USERNAME to username,
            UseCaseConstant.PASSWORD to password,
            UseCaseConstant.FULLNAME to fullName,
            UseCaseConstant.EMAIL to email
        ))?.addToJob()
    }
}

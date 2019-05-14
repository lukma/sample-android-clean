package com.lukma.clean.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukma.clean.domain.auth.usecase.RegisterUseCase
import com.lukma.clean.domain.common.UseCaseConstant
import com.lukma.clean.ui.common.Resource
import com.lukma.clean.ui.common.State
import com.lukma.clean.ui.common.base.BaseViewModel

class RegisterViewModel(private val registerUseCase: RegisterUseCase) : BaseViewModel() {
    private val registerActionMutable = MutableLiveData<Resource<Unit>>()
    internal val registerAction: LiveData<Resource<Unit>>
        get() = registerActionMutable

    fun register(username: String, password: String, fullName: String, email: String) {
        registerActionMutable.postValue(Resource(State.ON_REQUEST))
        val params = mapOf(
            UseCaseConstant.USERNAME to username,
            UseCaseConstant.PASSWORD to password,
            UseCaseConstant.FULLNAME to fullName,
            UseCaseConstant.EMAIL to email
        )
        registerUseCase.addParams(params)
            .onSuccess { registerActionMutable.postValue(Resource(State.ON_SUCCESS, it)) }
            .onError { registerActionMutable.postValue(Resource(State.ON_SUCCESS, null, it)) }
            .execute(viewModelScope)
    }
}

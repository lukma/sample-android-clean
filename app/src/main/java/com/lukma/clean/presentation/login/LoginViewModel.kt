package com.lukma.clean.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukma.clean.domain.auth.entity.ThirdParty
import com.lukma.clean.domain.auth.usecase.AuthorizeByThirdPartyUseCase
import com.lukma.clean.domain.auth.usecase.AuthorizeByUsernameOrEmailUseCase
import com.lukma.clean.domain.common.UseCaseConstant
import com.lukma.clean.presentation.common.Resource
import com.lukma.clean.presentation.common.State
import com.lukma.clean.presentation.common.base.BaseViewModel

class LoginViewModel(
    private val authorizeUseCase: AuthorizeByUsernameOrEmailUseCase,
    private val authorizeByThirdPartyUseCase: AuthorizeByThirdPartyUseCase
) : BaseViewModel() {

    private val authorizeActionMutable = MutableLiveData<Resource<Unit>>()
    internal val authorizeAction: LiveData<Resource<Unit>>
        get() = authorizeActionMutable

    private val authorizeByThirdPartyActionMutable = MutableLiveData<Resource<Unit>>()
    internal val authorizeByThirdPartyAction: LiveData<Resource<Unit>>
        get() = authorizeByThirdPartyActionMutable

    fun authorize(usernameOrEmail: String, password: String) {
        authorizeActionMutable.postValue(Resource(State.ON_REQUEST))
        val params = mapOf(
            UseCaseConstant.USERNAME to usernameOrEmail,
            UseCaseConstant.PASSWORD to password
        )
        authorizeUseCase.addParams(params)
            .onSuccess { authorizeActionMutable.postValue(Resource(State.ON_SUCCESS, it)) }
            .onError { authorizeActionMutable.postValue(Resource(State.ON_FAILURE, null, it)) }
            .execute(viewModelScope)
    }

    fun authorize(thirdParty: ThirdParty, token: String?) {
        authorizeByThirdPartyActionMutable.postValue(Resource(State.ON_REQUEST))
        val params = mapOf(
            UseCaseConstant.THIRD_PARTY to thirdParty,
            UseCaseConstant.TOKEN to token
        )
        authorizeByThirdPartyUseCase.addParams(params)
            .onSuccess { authorizeByThirdPartyActionMutable.postValue(Resource(State.ON_REQUEST)) }
            .onError { authorizeByThirdPartyActionMutable.postValue(Resource(State.ON_FAILURE, null, it)) }
            .execute(viewModelScope)
    }
}

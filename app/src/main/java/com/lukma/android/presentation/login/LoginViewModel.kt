package com.lukma.android.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukma.android.domain.auth.entity.ThirdParty
import com.lukma.android.domain.auth.usecase.AuthorizeByThirdPartyUseCase
import com.lukma.android.domain.auth.usecase.AuthorizeByUsernameOrEmailUseCase
import com.lukma.android.domain.common.UseCaseConstant
import com.lukma.android.presentation.common.entity.Resource
import com.lukma.android.shared.extensions.toResourceLiveData
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authorizeUseCase: AuthorizeByUsernameOrEmailUseCase,
    private val authorizeByThirdPartyUseCase: AuthorizeByThirdPartyUseCase
) : ViewModel() {

    private val authorizeActionMutable = MutableLiveData<Resource<Unit>>()
    internal val authorizeAction: LiveData<Resource<Unit>>
        get() = authorizeActionMutable

    private val authorizeByThirdPartyActionMutable = MutableLiveData<Resource<Unit>>()
    internal val authorizeByThirdPartyAction: LiveData<Resource<Unit>>
        get() = authorizeByThirdPartyActionMutable

    fun authorize(usernameOrEmail: String, password: String) {
        viewModelScope.launch {
            authorizeActionMutable.postValue(Resource.Loading)
            val params = mapOf(
                UseCaseConstant.USERNAME to usernameOrEmail,
                UseCaseConstant.PASSWORD to password
            )
            authorizeUseCase.addParams(params).invoke().toResourceLiveData(authorizeActionMutable)
        }
    }

    fun authorize(thirdParty: ThirdParty, token: String?) {
        viewModelScope.launch {
            authorizeByThirdPartyActionMutable.postValue(Resource.Loading)
            val params = mapOf(
                UseCaseConstant.THIRD_PARTY to thirdParty,
                UseCaseConstant.TOKEN to token
            )
            authorizeByThirdPartyUseCase.addParams(params).invoke()
                .toResourceLiveData(authorizeByThirdPartyActionMutable)
        }
    }
}

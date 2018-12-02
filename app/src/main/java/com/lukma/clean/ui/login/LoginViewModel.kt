package com.lukma.clean.ui.login

import androidx.lifecycle.ViewModel
import com.lukma.clean.domain.auth.interactor.AuthorizeByThirdParty
import com.lukma.clean.domain.auth.interactor.AuthorizeByUsernameOrEmail
import com.lukma.clean.domain.common.ThirdParty
import com.lukma.clean.ui.common.SingleLiveData

class LoginViewModel(
    private val authorizeByUsernameOrEmailUseCase: AuthorizeByUsernameOrEmail,
    private val authorizeByThirdPartyUseCase: AuthorizeByThirdParty
) : ViewModel() {
    internal val authorizeByUsernameOrEmailLiveData = SingleLiveData(authorizeByUsernameOrEmailUseCase::execute)
    internal val authorizeByThirdPartyLiveData = SingleLiveData(authorizeByThirdPartyUseCase::execute)

    fun authorize(usernameOrEmail: String, password: String) {
        authorizeByUsernameOrEmailLiveData.run(AuthorizeByUsernameOrEmail.Params(
            usernameOrEmail,
            password
        ))
    }

    fun authorize(thirdParty: ThirdParty, token: String?) {
        authorizeByThirdPartyLiveData.run(AuthorizeByThirdParty.Params(thirdParty, token.orEmpty()))
    }

    override fun onCleared() {
        authorizeByUsernameOrEmailUseCase.dispose()
        authorizeByThirdPartyUseCase.dispose()
    }
}
package com.lukma.clean.ui.login

import androidx.lifecycle.ViewModel
import com.lukma.clean.domain.auth.interactor.AuthorizeByThirdParty
import com.lukma.clean.domain.auth.interactor.AuthorizeByUsernameOrEmail
import com.lukma.clean.domain.common.entity.ThirdParty
import com.lukma.clean.domain.common.UseCaseConstant
import com.lukma.clean.ui.common.SingleLiveData

class LoginViewModel(
    authorizeByUsernameOrEmailUseCase: AuthorizeByUsernameOrEmail,
    authorizeByThirdPartyUseCase: AuthorizeByThirdParty
) : ViewModel() {
    internal val authorizeByUsernameOrEmailLiveData = SingleLiveData(authorizeByUsernameOrEmailUseCase::execute)
    internal val authorizeByThirdPartyLiveData = SingleLiveData(authorizeByThirdPartyUseCase::execute)

    fun authorize(usernameOrEmail: String, password: String) {
        authorizeByUsernameOrEmailLiveData.run(mapOf(
            UseCaseConstant.USERNAME to usernameOrEmail,
            UseCaseConstant.PASSWORD to password
        ))
    }

    fun authorize(thirdParty: ThirdParty, token: String?) {
        authorizeByThirdPartyLiveData.run(mapOf(
            UseCaseConstant.THIRD_PARTY to thirdParty,
            UseCaseConstant.TOKEN to token
        ))
    }
}
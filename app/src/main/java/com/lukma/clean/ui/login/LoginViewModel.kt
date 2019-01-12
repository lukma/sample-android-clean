package com.lukma.clean.ui.login

import com.lukma.clean.domain.auth.interactor.AuthorizeByThirdPartyUseCase
import com.lukma.clean.domain.auth.interactor.AuthorizeByUsernameOrEmailUseCase
import com.lukma.clean.domain.common.UseCaseConstant
import com.lukma.clean.domain.auth.entity.ThirdParty
import com.lukma.clean.ui.common.ResourceLiveData
import com.lukma.clean.ui.common.base.BaseViewModel

class LoginViewModel(
    authorizeByUsernameOrEmailUseCase: AuthorizeByUsernameOrEmailUseCase,
    authorizeByThirdPartyUseCase: AuthorizeByThirdPartyUseCase
) : BaseViewModel() {
    internal val authorizeByUsernameOrEmailLiveData = ResourceLiveData(authorizeByUsernameOrEmailUseCase)
    internal val authorizeByThirdPartyLiveData = ResourceLiveData(authorizeByThirdPartyUseCase)

    fun authorize(usernameOrEmail: String, password: String) {
        authorizeByUsernameOrEmailLiveData.execute(mapOf(
            UseCaseConstant.USERNAME to usernameOrEmail,
            UseCaseConstant.PASSWORD to password
        )).runBySupervisor()
    }

    fun authorize(thirdParty: ThirdParty, token: String?) {
        authorizeByThirdPartyLiveData.execute(mapOf(
            UseCaseConstant.THIRD_PARTY to thirdParty,
            UseCaseConstant.TOKEN to token
        )).runBySupervisor()
    }
}

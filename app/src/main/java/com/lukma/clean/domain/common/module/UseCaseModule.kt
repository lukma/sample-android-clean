package com.lukma.clean.domain.common.module

import com.lukma.clean.domain.auth.interactor.*
import com.lukma.clean.domain.content.interactor.GetListOfContentUseCase
import com.lukma.clean.domain.preference.interactor.GetFcmUseCase
import com.lukma.clean.domain.preference.interactor.SaveFcmUseCase
import org.koin.dsl.module.module
import org.koin.experimental.builder.factory

val useCaseModule = module {
    factory<SaveFcmUseCase>()

    factory<GetFcmUseCase>()

    factory<AuthorizeByUsernameOrEmailUseCase>()

    factory<AuthorizeByThirdPartyUseCase>()

    factory<RegisterUseCase>()

    factory<IsAuthenticatedUseCase>()

    factory<LogoutUseCase>()

    factory<GetListOfContentUseCase>()
}

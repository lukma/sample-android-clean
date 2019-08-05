package com.lukma.android.domain.common.module

import com.lukma.android.domain.auth.usecase.*
import com.lukma.android.domain.content.usecase.GetListOfContentUseCase
import com.lukma.android.domain.preference.usecase.GetFcmUseCase
import com.lukma.android.domain.preference.usecase.SaveFcmUseCase
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val useCaseModule = module {
    // Preference
    factory<SaveFcmUseCase>()
    factory<GetFcmUseCase>()

    // Auth
    factory<AuthorizeByUsernameOrEmailUseCase>()
    factory<AuthorizeByThirdPartyUseCase>()
    factory<RegisterUseCase>()
    factory<IsAuthenticatedUseCase>()
    factory<LogoutUseCase>()

    // Content
    factory<GetListOfContentUseCase>()
}

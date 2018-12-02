package com.lukma.clean.di

import com.lukma.clean.domain.auth.interactor.*
import com.lukma.clean.domain.content.interactor.GetContents
import org.koin.dsl.module.module
import org.koin.experimental.builder.factory

val useCaseModule = module {
    factory<AuthorizeByUsernameOrEmail>()

    factory<AuthorizeByThirdParty>()

    factory<Register>()

    factory<IsAuthenticated>()

    factory<Logout>()

    factory<GetContents>()
}
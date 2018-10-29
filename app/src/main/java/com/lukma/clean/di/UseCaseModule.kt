package com.lukma.clean.di

import com.lukma.clean.data.auth.interactor.*
import com.lukma.clean.data.content.interactor.GetContents
import org.koin.dsl.module.module
import org.koin.experimental.builder.factory

val useCaseModule = module {
    factory<IsAuthenticated>()

    factory<SignInWithEmail>()

    factory<SignInWithFacebook>()

    factory<SignInWithGoogle>()

    factory<Authorize>()

    factory<Register>()

    factory<Logout>()

    factory<GetContents>()
}
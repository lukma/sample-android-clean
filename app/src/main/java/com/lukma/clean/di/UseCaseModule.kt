package com.lukma.clean.di

import com.lukma.clean.data.auth.interactor.IsAuthenticated
import com.lukma.clean.data.auth.interactor.Login
import com.lukma.clean.data.auth.interactor.Logout
import com.lukma.clean.data.auth.interactor.Register
import com.lukma.clean.data.content.interactor.GetContents
import org.koin.dsl.module.module
import org.koin.experimental.builder.factory

val useCaseModule = module {
    factory<IsAuthenticated>()

    factory<Login>()

    factory<Register>()

    factory<Logout>()

    factory<GetContents>()
}
package com.lukma.clean.di

import com.lukma.clean.data.auth.AuthRepository
import com.lukma.clean.data.auth.AuthRepositoryImpl
import com.lukma.clean.data.auth.store.AuthMapper
import com.lukma.clean.data.auth.store.AuthStoreFactory
import com.lukma.clean.data.content.ContentRepository
import com.lukma.clean.data.content.ContentRepositoryImpl
import com.lukma.clean.data.content.store.ContentMapper
import com.lukma.clean.data.content.store.ContentStoreFactory
import org.koin.dsl.module.module
import org.koin.experimental.builder.factory
import org.koin.experimental.builder.single

val dataModule = module {
    single<AuthMapper>()

    factory<AuthStoreFactory>()

    single<AuthRepository> { AuthRepositoryImpl(get()) }

    single<ContentMapper>()

    factory<ContentStoreFactory>()

    single<ContentRepository> { ContentRepositoryImpl(get()) }
}
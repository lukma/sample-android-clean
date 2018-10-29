package com.lukma.clean.di

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.data.auth.AuthDataRepository
import com.lukma.clean.data.auth.store.AuthMapper
import com.lukma.clean.data.auth.store.AuthStoreFactory
import com.lukma.clean.domain.content.ContentRepository
import com.lukma.clean.data.content.ContentDataRepository
import com.lukma.clean.data.content.store.ContentMapper
import com.lukma.clean.data.content.store.ContentStoreFactory
import org.koin.dsl.module.module
import org.koin.experimental.builder.factory
import org.koin.experimental.builder.single

val dataModule = module {
    single<AuthMapper>()

    factory<AuthStoreFactory>()

    single<AuthRepository> { AuthDataRepository(get()) }

    single<ContentMapper>()

    factory<ContentStoreFactory>()

    single<ContentRepository> { ContentDataRepository(get()) }
}
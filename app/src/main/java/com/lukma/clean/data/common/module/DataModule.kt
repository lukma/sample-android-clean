package com.lukma.clean.data.common.module

import com.lukma.clean.data.auth.AuthDataRepository
import com.lukma.clean.data.content.ContentDataRepository
import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.content.ContentRepository
import org.koin.dsl.module.module

val dataModule = module {
    single<AuthRepository> { AuthDataRepository(get(), get()) }

    single<ContentRepository> { ContentDataRepository(get(), get()) }
}
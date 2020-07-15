package com.lukma.core.domain

import com.lukma.core.domain.account.usecase.GetProfileUseCase
import com.lukma.core.domain.auth.usecase.IsLoggedInUseCase
import com.lukma.core.domain.auth.usecase.SignInWithEmailUseCase
import com.lukma.core.domain.auth.usecase.SignUpWithEmailUseCase
import com.lukma.core.domain.chat.usecase.GetChatMessagesUseCase
import com.lukma.core.domain.chat.usecase.GetChatRoomByMembersUseCase
import com.lukma.core.domain.chat.usecase.GetChatRoomsUseCase
import com.lukma.core.domain.chat.usecase.SendChatMessageUseCase
import com.lukma.core.domain.user.usecase.SearchUsersUseCase
import org.koin.dsl.module
import org.koin.experimental.builder.factory

val useCaseModule = module {
    // Auth
    factory<IsLoggedInUseCase>()
    factory<SignInWithEmailUseCase>()
    factory<SignUpWithEmailUseCase>()

    // Account
    factory<GetProfileUseCase>()

    // Chat
    factory<GetChatRoomsUseCase>()
    factory<GetChatRoomByMembersUseCase>()
    factory<GetChatMessagesUseCase>()
    factory<SendChatMessageUseCase>()

    // User
    factory<SearchUsersUseCase>()
}

package com.lukma.core.data

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import com.lukma.core.data.account.AccountRepositoryData
import com.lukma.core.data.auth.AuthRepositoryData
import com.lukma.core.data.chat.ChatRepositoryData
import com.lukma.core.data.user.UserRepositoryData
import com.lukma.core.domain.account.AccountRepository
import com.lukma.core.domain.auth.AuthRepository
import com.lukma.core.domain.chat.ChatRepository
import com.lukma.core.domain.user.UserRepository
import org.koin.dsl.module
import org.koin.experimental.builder.factoryBy

val repositoryModule = module {
    single { Firebase.auth }
    single {
        Firebase.firestore.apply {
            firestoreSettings = firestoreSettings {
                isPersistenceEnabled = true
            }
        }
    }

    factoryBy<AuthRepository, AuthRepositoryData>()
    factoryBy<AccountRepository, AccountRepositoryData>()
    factory<ChatRepository> {
        ChatRepositoryData(
            get(),
            get<FirebaseFirestore>().collection("chatRooms"),
            get<FirebaseFirestore>().collection("chatMessages")
        )
    }
    factoryBy<UserRepository, UserRepositoryData>()
}

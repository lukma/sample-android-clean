package com.lukma.clean.di

import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.lukma.clean.R
import com.lukma.clean.data.common.SessionManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import org.koin.experimental.builder.single

val appModule = module {
    single<SessionManager>()

    single { FirebaseAuth.getInstance() }

    single { CallbackManager.Factory.create() }

    single { LoginManager.getInstance() }

    single {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(androidContext().getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
    }
}
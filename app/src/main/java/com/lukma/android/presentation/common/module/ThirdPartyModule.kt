package com.lukma.android.presentation.common.module

import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.lukma.android.R
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val thirdPartyModule = module {
    single { CallbackManager.Factory.create() }

    single { LoginManager.getInstance() }

    single {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(androidContext().getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
}

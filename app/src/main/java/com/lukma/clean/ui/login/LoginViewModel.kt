package com.lukma.clean.ui.login

import androidx.lifecycle.ViewModel
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.lukma.clean.data.auth.interactor.*
import com.lukma.clean.data.common.SessionManager
import com.lukma.clean.ui.common.SingleFetchData

class LoginViewModel(
        private val sessionManager: SessionManager,
        private val signInWithEmailUseCase: SignInWithEmail,
        private val signInWithFacebookUseCase: SignInWithFacebook,
        private val signInWithGoogleUseCase: SignInWithGoogle,
        private val registerUseCase: Register,
        private val authorizeUseCase: Authorize
) : ViewModel() {
    val signInWithEmailFetchData = SingleFetchData(signInWithEmailUseCase::execute)
    val signInWithFacebookFetchData = SingleFetchData(signInWithFacebookUseCase::execute)
    val signInWithGoogleFetchData = SingleFetchData(signInWithGoogleUseCase::execute)
    val registerFetchData = SingleFetchData(registerUseCase::execute)
    val authorizeFetchData = SingleFetchData(authorizeUseCase::execute)

    fun signIn(email: String, password: String) {
        signInWithEmailFetchData.run(SignInWithEmail.Params(email, password))
    }

    fun signIn(token: AccessToken) {
        signInWithFacebookFetchData.run(SignInWithFacebook.Params(token.token))
    }

    fun signIn(acct: GoogleSignInAccount?) {
        signInWithGoogleFetchData.run(SignInWithGoogle.Params(acct?.idToken.orEmpty()))
    }

    fun register(faId: String) {
        registerFetchData.run(Register.Params(faId, sessionManager.getFcmId()))
    }

    fun authorize(faId: String) {
        authorizeFetchData.run(Authorize.Params(faId, sessionManager.getFcmId()))
    }

    override fun onCleared() {
        signInWithEmailUseCase.dispose()
        signInWithFacebookUseCase.dispose()
        signInWithGoogleUseCase.dispose()
        registerUseCase.dispose()
        authorizeUseCase.dispose()
    }
}
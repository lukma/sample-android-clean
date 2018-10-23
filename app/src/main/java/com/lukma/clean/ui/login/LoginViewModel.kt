package com.lukma.clean.ui.login

import androidx.lifecycle.ViewModel
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.lukma.clean.data.auth.interactor.Login
import com.lukma.clean.data.auth.interactor.Register
import com.lukma.clean.data.common.SessionManager
import com.lukma.clean.ui.common.SingleFetchData

class LoginViewModel(
        private val firebaseAuth: FirebaseAuth,
        private val sessionManager: SessionManager,
        private val loginUseCase: Login,
        private val registerUseCase: Register
) : ViewModel() {
    val loginFetchData = SingleFetchData(loginUseCase::execute)
    val registerFetchData = SingleFetchData(registerUseCase::execute)

    fun loginWithEmail(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        authenticated()
                    } else {
                        loginFetchData.error.value = Exception("Authentication failed.")
                    }
                }
    }

    fun connectSocialMedia(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (task.result?.additionalUserInfo?.isNewUser == true) {
                            register(facebookToken = token.token)
                        } else {
                            authenticated()
                        }
                    } else {
                        loginFetchData.error.value = Exception("Authentication failed.")
                    }
                }
    }

    fun connectSocialMedia(acct: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        if (task.result?.additionalUserInfo?.isNewUser == true) {
                            register(googleToken = acct?.idToken.orEmpty())
                        } else {
                            authenticated()
                        }
                    } else {
                        loginFetchData.error.value = Exception("Authentication failed.")
                    }
                }
    }

    fun authenticated() {
        loginFetchData.run(Login.Params(
                firebaseAuth.currentUser?.uid.orEmpty(),
                sessionManager.getFcmId()
        ))
    }

    private fun register(facebookToken: String = "", googleToken: String = "") {
        registerFetchData.run(Register.Params(
                firebaseAuth.currentUser?.uid.orEmpty(),
                sessionManager.getFcmId(),
                facebookToken,
                googleToken
        ))
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    override fun onCleared() {
        loginUseCase.dispose()
        registerUseCase.dispose()
    }
}
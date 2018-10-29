package com.lukma.clean.ui.register

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.lukma.clean.data.auth.interactor.Register
import com.lukma.clean.data.common.SessionManager
import com.lukma.clean.ui.common.SingleFetchData

class RegisterViewModel(
        private val firebaseAuth: FirebaseAuth,
        private val sessionManager: SessionManager,
        private val useCase: Register
) : ViewModel() {
    val fetchData = SingleFetchData(useCase::execute)

    fun createUserWithEmailAndPassword(email: String, password: String, fullname: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                updateProfile(fullname)
            } else {
                fetchData.error.value = Exception("Authentication failed.")
            }
        }
    }

    private fun updateProfile(fullname: String) {
        val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(fullname)
                .build()

        firebaseAuth.currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                register()
            } else {
                fetchData.error.value = Exception("Authentication failed.")
            }
        }
    }

    private fun register() {
        fetchData.run(Register.Params(
                firebaseAuth.currentUser?.uid.orEmpty(),
                sessionManager.getFcmId()
        ))
    }

    override fun onCleared() {
        useCase.dispose()
    }
}
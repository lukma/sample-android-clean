package com.lukma.clean.ui.register

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.lukma.clean.data.common.SessionManager
import com.lukma.clean.domain.auth.interactor.CreateUserWithEmailAndPassword
import com.lukma.clean.domain.auth.interactor.Register
import com.lukma.clean.domain.auth.interactor.UpdateProfile
import com.lukma.clean.ui.common.SingleFetchData

class RegisterViewModel(
        private val firebaseAuth: FirebaseAuth,
        private val sessionManager: SessionManager,
        private val createUserWithEmailAndPasswordUseCase: CreateUserWithEmailAndPassword,
        private val updateProfileUseCase: UpdateProfile,
        private val registerUseCase: Register
) : ViewModel() {
    val createUserWithEmailAndPasswordFetchData = SingleFetchData(createUserWithEmailAndPasswordUseCase::execute)
    val updateProfileFetchData = SingleFetchData(updateProfileUseCase::execute)
    val registerFetchData = SingleFetchData(registerUseCase::execute)

    fun createUserWithEmailAndPassword(email: String, password: String) {
        createUserWithEmailAndPasswordFetchData.run(CreateUserWithEmailAndPassword.Params(
                email,
                password
        ))
    }

    fun updateProfile(fullName: String) {
        updateProfileFetchData.run(UpdateProfile.Params(fullName))
    }

    fun register() {
        registerFetchData.run(Register.Params(
                firebaseAuth.currentUser?.uid.orEmpty(),
                sessionManager.getFcmId()
        ))
    }

    override fun onCleared() {
        createUserWithEmailAndPasswordUseCase.dispose()
        updateProfileUseCase.dispose()
        registerUseCase.dispose()
    }
}
package com.lukma.clean.ui.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.lukma.clean.data.auth.interactor.Logout
import com.lukma.clean.ui.common.SingleFetchData

class ProfileViewModel(
        private val firebaseAuth: FirebaseAuth,
        private val useCase: Logout
) : ViewModel() {
    private val fetchData = SingleFetchData(useCase::execute)

    fun logout() {
        firebaseAuth.signOut()
        fetchData.run()
    }

    override fun onCleared() {
        useCase.dispose()
    }
}
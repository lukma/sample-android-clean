package com.lukma.android.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lukma.core.domain.account.Profile
import com.lukma.core.domain.account.usecase.GetProfileUseCase
import com.lukma.core.domain.auth.usecase.IsLoggedInUseCase
import com.lukma.core.domain.getOrNull
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val isLoggedInUseCase: IsLoggedInUseCase by inject()
    private val getProfileUseCase: GetProfileUseCase by inject()

    private val isLoggedInMutable = MutableLiveData<Boolean>()
    internal val isLoggedIn: LiveData<Boolean> = isLoggedInMutable

    private val profileMutable = MutableLiveData<Profile>()
    internal val profile: LiveData<Profile> = profileMutable

    suspend fun isLogged() {
        val result = isLoggedInUseCase.invoke().getOrNull() ?: false
        isLoggedInMutable.postValue(result)
    }

    suspend fun getProfile() {
        val result = getProfileUseCase.invoke().getOrNull() ?: return
        profileMutable.postValue(result)
    }
}

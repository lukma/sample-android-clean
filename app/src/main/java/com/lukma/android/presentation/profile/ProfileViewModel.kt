package com.lukma.android.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukma.android.domain.auth.usecase.LogoutUseCase

class ProfileViewModel(private val logoutUseCase: LogoutUseCase) : ViewModel() {
    private val logoutActionMutable = MutableLiveData<Unit>()
    internal val logoutAction: LiveData<Unit>
        get() = logoutActionMutable

    fun logout() {
        logoutUseCase.onSuccess(logoutActionMutable::postValue).execute(viewModelScope)
    }
}

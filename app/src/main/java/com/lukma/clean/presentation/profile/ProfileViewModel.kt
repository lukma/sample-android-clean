package com.lukma.clean.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukma.clean.domain.auth.usecase.LogoutUseCase
import com.lukma.clean.presentation.common.base.BaseViewModel

class ProfileViewModel(private val logoutUseCase: LogoutUseCase) : BaseViewModel() {
    private val logoutActionMutable = MutableLiveData<Unit>()
    internal val logoutAction: LiveData<Unit>
        get() = logoutActionMutable

    fun logout() {
        logoutUseCase.onSuccess { logoutActionMutable.postValue(it) }.execute(viewModelScope)
    }
}

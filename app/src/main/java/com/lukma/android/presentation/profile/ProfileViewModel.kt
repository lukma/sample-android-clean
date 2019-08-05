package com.lukma.android.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukma.android.domain.auth.usecase.LogoutUseCase
import com.lukma.android.presentation.common.base.BaseViewModel

class ProfileViewModel(private val logoutUseCase: LogoutUseCase) : BaseViewModel() {
    private val logoutActionMutable = MutableLiveData<Unit>()
    internal val logoutAction: LiveData<Unit>
        get() = logoutActionMutable

    fun logout() {
        logoutUseCase.onSuccess(logoutActionMutable::postValue).execute(viewModelScope)
    }
}

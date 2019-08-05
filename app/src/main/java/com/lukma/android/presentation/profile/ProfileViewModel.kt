package com.lukma.android.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukma.android.domain.auth.usecase.LogoutUseCase
import com.lukma.android.shared.extensions.toLiveData
import kotlinx.coroutines.launch

class ProfileViewModel(private val logoutUseCase: LogoutUseCase) : ViewModel() {
    private val logoutActionMutable = MutableLiveData<Unit>()
    internal val logoutAction: LiveData<Unit>
        get() = logoutActionMutable

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.invoke().toLiveData(logoutActionMutable)
        }
    }
}

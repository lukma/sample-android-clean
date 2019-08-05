package com.lukma.android.presentation.common.module

import com.lukma.android.presentation.home.HomeViewModel
import com.lukma.android.presentation.login.LoginViewModel
import com.lukma.android.presentation.profile.ProfileViewModel
import com.lukma.android.presentation.register.RegisterViewModel
import com.lukma.android.presentation.splash.SplashViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<SplashViewModel>()

    viewModel<LoginViewModel>()

    viewModel<RegisterViewModel>()

    viewModel<HomeViewModel>()

    viewModel<ProfileViewModel>()
}

package com.lukma.clean.presentation.common.module

import com.lukma.clean.presentation.home.HomeViewModel
import com.lukma.clean.presentation.login.LoginViewModel
import com.lukma.clean.presentation.profile.ProfileViewModel
import com.lukma.clean.presentation.register.RegisterViewModel
import com.lukma.clean.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }

    viewModel { LoginViewModel(get(), get()) }

    viewModel { RegisterViewModel(get()) }

    viewModel { HomeViewModel(get()) }

    viewModel { ProfileViewModel(get()) }
}

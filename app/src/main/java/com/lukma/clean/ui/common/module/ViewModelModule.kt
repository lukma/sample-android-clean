package com.lukma.clean.ui.common.module

import com.lukma.clean.ui.home.HomeViewModel
import com.lukma.clean.ui.login.LoginViewModel
import com.lukma.clean.ui.profile.ProfileViewModel
import com.lukma.clean.ui.register.RegisterViewModel
import com.lukma.clean.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }

    viewModel { LoginViewModel(get(), get()) }

    viewModel { RegisterViewModel(get()) }

    viewModel { HomeViewModel(get()) }

    viewModel { ProfileViewModel(get()) }
}

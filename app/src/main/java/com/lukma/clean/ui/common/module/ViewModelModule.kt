package com.lukma.clean.ui.common.module

import com.lukma.clean.ui.auth.AuthViewModel
import com.lukma.clean.ui.home.HomeViewModel
import com.lukma.clean.ui.login.LoginViewModel
import com.lukma.clean.ui.main.MainViewModel
import com.lukma.clean.ui.notifications.NotificationsViewModel
import com.lukma.clean.ui.profile.ProfileViewModel
import com.lukma.clean.ui.register.RegisterViewModel
import com.lukma.clean.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel<SplashViewModel>()

    viewModel<AuthViewModel>()

    viewModel<LoginViewModel>()

    viewModel<RegisterViewModel>()

    viewModel<MainViewModel>()

    viewModel<HomeViewModel>()

    viewModel<NotificationsViewModel>()

    viewModel<ProfileViewModel>()
}

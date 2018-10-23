package com.lukma.clean.di

import com.lukma.clean.ui.auth.AuthViewModel
import com.lukma.clean.ui.home.HomeViewModel
import com.lukma.clean.ui.login.LoginViewModel
import com.lukma.clean.ui.main.MainViewModel
import com.lukma.clean.ui.notifications.NotificationsViewModel
import com.lukma.clean.ui.profile.ProfileViewModel
import com.lukma.clean.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel<AuthViewModel>()

    viewModel<LoginViewModel>()

    viewModel<RegisterViewModel>()

    viewModel<MainViewModel>()

    viewModel<HomeViewModel>()

    viewModel<NotificationsViewModel>()

    viewModel<ProfileViewModel>()
}
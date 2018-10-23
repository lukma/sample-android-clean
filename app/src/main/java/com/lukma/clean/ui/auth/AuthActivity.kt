package com.lukma.clean.ui.auth

import com.lukma.clean.R
import com.lukma.clean.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity : BaseActivity<AuthViewModel>() {
    override val resourceLayout = R.layout.activity_auth
    override val activityViewModel by viewModel<AuthViewModel>()
}

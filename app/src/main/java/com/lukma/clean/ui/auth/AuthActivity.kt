package com.lukma.clean.ui.auth

import androidx.navigation.fragment.NavHostFragment
import com.lukma.clean.R
import com.lukma.clean.ui.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity : BaseActivity<AuthViewModel>() {
    override val resourceLayout = R.layout.activity_auth
    override val viewModel by viewModel<AuthViewModel>()

    override fun onCreateNavController() = hostFragment as NavHostFragment
}

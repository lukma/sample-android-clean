package com.lukma.clean.presentation.auth

import androidx.navigation.fragment.NavHostFragment
import com.lukma.clean.R
import com.lukma.clean.presentation.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class AuthActivity : BaseActivity() {
    override val resourceLayout = R.layout.activity_auth

    override fun onCreateNavController() = hostFragment as NavHostFragment
}

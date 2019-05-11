package com.lukma.clean.ui.auth

import androidx.navigation.fragment.NavHostFragment
import com.lukma.clean.R
import com.lukma.clean.ui.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class AuthActivity : BaseActivity() {
    override val resourceLayout = R.layout.activity_auth

    override fun onCreateNavController() = hostFragment as NavHostFragment
}

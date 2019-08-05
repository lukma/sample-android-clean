package com.lukma.android.presentation.auth

import androidx.navigation.fragment.NavHostFragment
import com.lukma.android.R
import com.lukma.android.presentation.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class AuthActivity : BaseActivity() {
    override val resourceLayout = R.layout.activity_auth

    override fun onCreateNavController() = hostFragment as NavHostFragment
}

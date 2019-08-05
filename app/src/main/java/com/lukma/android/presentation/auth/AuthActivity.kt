package com.lukma.android.presentation.auth

import androidx.navigation.fragment.NavHostFragment
import com.lukma.android.R
import com.lukma.android.presentation.common.base.BaseActivityNav
import kotlinx.android.synthetic.main.activity_main.*

class AuthActivity : BaseActivityNav() {
    override val resourceLayout = R.layout.activity_auth
    override val navHost = hostFragment as NavHostFragment
}

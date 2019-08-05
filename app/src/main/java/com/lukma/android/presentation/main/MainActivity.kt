package com.lukma.android.presentation.main

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.lukma.android.R
import com.lukma.android.presentation.common.base.BaseActivityNav
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivityNav() {
    override val resourceLayout = R.layout.activity_main
    override val navHost = hostFragment as? NavHostFragment

    override fun onInitViews() {
        navController?.also {
            setupActionBarWithNavController(this, it)
            setupWithNavController(navigation, it)
        }
    }
}

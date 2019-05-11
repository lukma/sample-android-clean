package com.lukma.clean.ui.main

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.lukma.clean.R
import com.lukma.clean.ui.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override val resourceLayout = R.layout.activity_main

    override fun onCreateNavController() = hostFragment as NavHostFragment

    override fun onInitViews() {
        navController?.let {
            setupActionBarWithNavController(this, it)
            setupWithNavController(navigation, it)
        }
    }
}

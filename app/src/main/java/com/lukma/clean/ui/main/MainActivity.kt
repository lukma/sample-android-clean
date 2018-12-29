package com.lukma.clean.ui.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.lukma.clean.R
import com.lukma.clean.ui.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel>() {
    override val resourceLayout = R.layout.activity_main
    override val viewModel by viewModel<MainViewModel>()

    override fun buildNavController() = NavHostFragment.findNavController(hostFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController?.let {
            setupActionBarWithNavController(this, it)
            setupWithNavController(navigation, it)
        }

        lifecycle.addObserver(MainActivityComponent())
    }
}
package com.lukma.clean.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.lukma.clean.R
import com.lukma.clean.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainViewModel>() {
    override val resourceLayout = R.layout.activity_main
    override val activityViewModel by viewModel<MainViewModel>()

    private val navController by lazy { NavHostFragment.findNavController(hostFragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupActionBarWithNavController(this, navController)
        setupWithNavController(navigation, navController)

        lifecycle.addObserver(MainActivityComponent())

        activityViewModel.fetchData.data.observe(this, Observer {
            if (!it) {
                finish()
                navController.navigate(R.id.action_authActivity)
            }
        })
    }
}

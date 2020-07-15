package com.lukma.android.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.lukma.android.R
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    private val navController by lazy { navHostFragment.findNavController() }

    init {
        lifecycleScope.launchWhenCreated {
            viewModel.isLogged()
            viewModel.getProfile()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel.isLoggedIn.observe(this) {
            if (!it) {
                navController.navigate(R.id.action_to_loginFragment)
            }
        }
        viewModel.profile.observe(this) {

        }
    }
}

package com.lukma.clean.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {
    abstract val resourceLayout: Int
    abstract val viewModel: VM
    abstract val navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(resourceLayout)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()
}
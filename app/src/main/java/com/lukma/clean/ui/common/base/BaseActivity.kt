package com.lukma.clean.ui.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

abstract class BaseActivity<VM : ViewModel> : AppCompatActivity() {
    abstract val resourceLayout: Int?
    abstract val viewModel: VM
    protected val navController by lazy { buildNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resourceLayout?.let(this::setContentView)
    }

    override fun onSupportNavigateUp() = navController?.navigateUp() ?: super.onSupportNavigateUp()

    protected open fun buildNavController(): NavController? = null
}
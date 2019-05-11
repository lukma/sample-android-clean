package com.lukma.clean.ui.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

abstract class BaseActivity : AppCompatActivity() {
    abstract val resourceLayout: Int?

    protected val navController by lazy {
        onCreateNavController()?.let(NavHostFragment::findNavController)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resourceLayout?.let(this::setContentView)
        onInitViews()
    }

    override fun onSupportNavigateUp() = navController?.navigateUp() ?: super.onSupportNavigateUp()

    protected open fun onCreateNavController(): NavHostFragment? = null

    protected open fun onInitViews() = Unit
}

package com.lukma.android.presentation.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment

abstract class BaseActivity : AppCompatActivity() {
    abstract val resourceLayout: Int?
    protected val navController by lazy {
        onCreateNavController()?.let(NavHostFragment::findNavController)
    }

    init {
        lifecycleScope.launchWhenCreated {
            onInitViews()
            onInitObservers()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resourceLayout?.run(this::setContentView)
    }

    override fun onSupportNavigateUp() = navController?.navigateUp() ?: super.onSupportNavigateUp()

    protected open fun onCreateNavController(): NavHostFragment? = null

    protected open fun onInitViews() {}

    protected open fun onInitObservers() {}
}

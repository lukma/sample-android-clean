package com.lukma.android.presentation.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

abstract class BaseActivity : AppCompatActivity() {
    abstract val resourceLayout: Int?

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

    protected open fun onInitViews() {}

    protected open fun onInitObservers() {}
}

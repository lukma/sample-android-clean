package com.lukma.clean.ui.common.base

import android.os.Bundle
import androidx.lifecycle.ViewModel

abstract class BaseActivityVM<VM : ViewModel> : BaseActivity() {
    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInitObservers()
    }

    protected open fun onInitObservers() = Unit
}

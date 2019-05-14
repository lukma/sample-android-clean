package com.lukma.clean.presentation.common.base

import android.os.Bundle
import androidx.lifecycle.ViewModel

abstract class BaseFragmentVM<VM : ViewModel> : BaseFragment() {
    abstract val viewModel: VM

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onInitObservers()
    }

    protected open fun onInitObservers() = Unit
}

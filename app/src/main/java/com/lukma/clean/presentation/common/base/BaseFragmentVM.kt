package com.lukma.clean.presentation.common.base

import androidx.lifecycle.ViewModel

abstract class BaseFragmentVM<VM : ViewModel> : BaseFragment() {
    abstract val viewModel: VM
}

package com.lukma.android.presentation.common.base

import androidx.lifecycle.ViewModel

abstract class BaseActivityVM<VM : ViewModel> : BaseActivity() {
    abstract val viewModel: VM
}

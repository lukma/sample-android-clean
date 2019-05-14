package com.lukma.clean.presentation.splash

import androidx.lifecycle.Observer
import com.lukma.clean.extensions.startActivityClearTask
import com.lukma.clean.presentation.common.base.BaseActivityVM
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivityVM<SplashViewModel>() {
    override val resourceLayout: Nothing? = null
    override val viewModel by viewModel<SplashViewModel>()

    override fun onInitObservers() {
        viewModel.launchToNextScreenAction.observe(this, Observer {
            startActivityClearTask(it)
        })
    }
}
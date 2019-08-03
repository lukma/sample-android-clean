package com.lukma.clean.presentation.splash

import androidx.lifecycle.Observer
import com.lukma.clean.presentation.common.base.BaseActivityVM
import com.lukma.clean.shared.extensions.startActivityClearTask
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivityVM<SplashViewModel>() {
    override val resourceLayout: Nothing? = null
    override val viewModel by viewModel<SplashViewModel>()

    override fun onStart() {
        super.onStart()
        viewModel.delayToNextScreen()
    }

    override fun onInitObservers() {
        viewModel.launchToNextScreenAction.observe(this, Observer {
            startActivityClearTask(it)
        })
    }
}

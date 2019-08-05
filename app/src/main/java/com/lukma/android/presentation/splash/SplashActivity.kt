package com.lukma.android.presentation.splash

import androidx.lifecycle.Observer
import com.lukma.android.presentation.common.base.BaseActivity
import com.lukma.android.shared.extensions.startActivityClearTask
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {
    override val resourceLayout: Nothing? = null
    private val viewModel by viewModel<SplashViewModel>()

    override fun onInitViews() {
        viewModel.delayToNextScreen()
    }

    override fun onInitObservers() {
        viewModel.launchToNextScreenAction.observe(this, Observer(::startActivityClearTask))
    }
}

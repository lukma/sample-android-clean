package com.lukma.clean.ui.splash

import androidx.lifecycle.Observer
import com.lukma.clean.extensions.startActivityClearTask
import com.lukma.clean.ui.common.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<SplashViewModel>() {
    override val resourceLayout: Nothing? = null
    override val viewModel by viewModel<SplashViewModel>()

    override fun onInitObservers() {
        super.onInitObservers()
        viewModel.launchToNextScreenLiveData.observe(this, Observer {
            startActivityClearTask(it)
        })
    }
}

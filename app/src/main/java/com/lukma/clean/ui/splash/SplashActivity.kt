package com.lukma.clean.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.lukma.clean.ui.auth.AuthActivity
import com.lukma.clean.ui.common.base.BaseActivity
import com.lukma.clean.ui.main.MainActivity
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<SplashViewModel>() {
    companion object {
        private const val DELAY_TO_NEXT_SCREEN = 3000L
    }

    override val resourceLayout: Nothing? = null
    override val viewModel by viewModel<SplashViewModel>()

    private var delayJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.isAuthenticatedLiveData.observe(this, Observer {
            val intent = if (it.data != true) AuthActivity::class.java
            else MainActivity::class.java
            startActivity(Intent(applicationContext, intent).addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            ))
        })
    }

    override fun onStart() {
        super.onStart()
        delayJob = GlobalScope.launch(Dispatchers.Main) {
            delay(DELAY_TO_NEXT_SCREEN)
            viewModel.isAuthenticated()
        }
    }

    override fun onStop() {
        delayJob?.cancel()
        super.onStop()
    }
}

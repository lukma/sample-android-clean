package com.lukma.android.presentation.profile

import androidx.lifecycle.Observer
import com.lukma.android.R
import com.lukma.android.presentation.auth.AuthActivity
import com.lukma.android.presentation.common.base.BaseFragment
import com.lukma.android.shared.extensions.startActivityClearTask
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {
    override val resourceLayout = R.layout.fragment_profile
    private val viewModel by viewModel<ProfileViewModel>()

    override fun onInitViews() {
        logoutButton.setOnClickListener { viewModel.logout() }
    }

    override fun onInitObservers() {
        viewModel.logoutAction.observe(this, Observer {
            context?.startActivityClearTask(AuthActivity::class.java)
        })
    }
}

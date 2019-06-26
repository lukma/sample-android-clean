package com.lukma.clean.presentation.profile

import androidx.lifecycle.Observer
import com.lukma.clean.R
import com.lukma.clean.presentation.auth.AuthActivity
import com.lukma.clean.presentation.common.base.BaseFragmentVM
import com.lukma.clean.shared.extensions.startActivityClearTask
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragmentVM<ProfileViewModel>() {
    override val resourceLayout = R.layout.fragment_profile
    override val viewModel by viewModel<ProfileViewModel>()

    override fun onInitViews() {
        logoutButton.setOnClickListener { viewModel.logout() }
    }

    override fun onInitObservers() {
        viewModel.logoutAction.observe(this, Observer {
            context?.startActivityClearTask(AuthActivity::class.java)
        })
    }
}

package com.lukma.clean.ui.profile

import androidx.lifecycle.Observer
import com.lukma.clean.R
import com.lukma.clean.extensions.startActivityClearTask
import com.lukma.clean.ui.auth.AuthActivity
import com.lukma.clean.ui.common.base.BaseFragmentVM
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragmentVM<ProfileViewModel>() {
    override val resourceLayout = R.layout.fragment_profile
    override val viewModel by viewModel<ProfileViewModel>()

    override fun onInitViews() {
        logoutButton.setOnClickListener { viewModel.logout() }
    }

    override fun onInitObservers() {
        viewModel.logoutLiveData.observe(this, Observer {
            context?.startActivityClearTask(AuthActivity::class.java)
        })
    }
}

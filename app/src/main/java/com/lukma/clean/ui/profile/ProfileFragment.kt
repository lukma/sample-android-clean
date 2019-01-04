package com.lukma.clean.ui.profile

import androidx.lifecycle.Observer
import com.lukma.clean.R
import com.lukma.clean.extensions.startActivityClearTask
import com.lukma.clean.ui.auth.AuthActivity
import com.lukma.clean.ui.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<ProfileViewModel>() {
    override val resourceLayout = R.layout.fragment_profile
    override val viewModel by viewModel<ProfileViewModel>()

    override fun onInitViews() {
        super.onInitViews()
        logoutButton.setOnClickListener { viewModel.logout() }
    }

    override fun onInitObservers() {
        super.onInitObservers()
        viewModel.logoutLiveData.observe(this, Observer {
            context?.startActivityClearTask(AuthActivity::class.java)
        })
    }
}

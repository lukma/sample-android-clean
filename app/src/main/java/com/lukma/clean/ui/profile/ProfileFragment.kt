package com.lukma.clean.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.lukma.clean.R
import com.lukma.clean.ui.auth.AuthActivity
import com.lukma.clean.ui.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<ProfileViewModel>() {
    override val resourceLayout = R.layout.fragment_profile
    override val viewModel by viewModel<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logoutButton.setOnClickListener {
            viewModel.logout()
        }

        viewModel.liveData.observe(this, Observer {
            startActivity(
                Intent(requireContext(), AuthActivity::class.java).addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                )
            )
        })
    }
}
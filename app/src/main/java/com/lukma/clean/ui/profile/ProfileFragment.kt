package com.lukma.clean.ui.profile

import android.os.Bundle
import android.view.View
import com.lukma.clean.R
import com.lukma.clean.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment<ProfileViewModel>() {
    override val resourceLayout = R.layout.fragment_profile
    override val fragmentViewModel by viewModel<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logoutButton.setOnClickListener {
            fragmentViewModel.logout()
        }
    }
}
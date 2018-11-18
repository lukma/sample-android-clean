package com.lukma.clean.ui.register

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.lukma.clean.R
import com.lukma.clean.extensions.handleError
import com.lukma.clean.extensions.showSnackBar
import com.lukma.clean.extensions.hideKeyboard
import com.lukma.clean.ui.base.BaseFragment
import com.lukma.clean.ui.common.SingleFetchData
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<RegisterViewModel>() {
    override val resourceLayout = R.layout.fragment_register
    override val fragmentViewModel by viewModel<RegisterViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerButton.setOnClickListener {
            hideKeyboard()
            fragmentViewModel.createUserWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
            )
        }

        fragmentViewModel.createUserWithEmailAndPasswordFetchData.state.observe(this, Observer {
            registerButton.isVisible = it != SingleFetchData.State.ON_REQUEST
            progressBar.isVisible = it == SingleFetchData.State.ON_REQUEST
        })
        fragmentViewModel.createUserWithEmailAndPasswordFetchData.data.observe(this, Observer {
            fragmentViewModel.updateProfile(fullnameEditText.text.toString())
        })
        fragmentViewModel.createUserWithEmailAndPasswordFetchData.error.observe(this, Observer(this::handleError))

        fragmentViewModel.updateProfileFetchData.state.observe(this, Observer {
            registerButton.isVisible = it != SingleFetchData.State.ON_REQUEST
            progressBar.isVisible = it == SingleFetchData.State.ON_REQUEST
        })
        fragmentViewModel.updateProfileFetchData.data.observe(this, Observer {
            fragmentViewModel.register()
        })
        fragmentViewModel.updateProfileFetchData.error.observe(this, Observer(this::handleError))

        fragmentViewModel.registerFetchData.state.observe(this, Observer {
            registerButton.isVisible = it != SingleFetchData.State.ON_REQUEST
            progressBar.isVisible = it == SingleFetchData.State.ON_REQUEST
        })
        fragmentViewModel.registerFetchData.data.observe(this, Observer {
            if (it.isNotEmpty()) {
                showSnackBar(getString(R.string.message_register_successfully))
            } else {
                showSnackBar(getString(R.string.message_register_failure))
            }
        })
        fragmentViewModel.registerFetchData.error.observe(this, Observer(this::handleError))
    }
}

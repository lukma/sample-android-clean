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
                    passwordEditText.text.toString(),
                    fullnameEditText.text.toString()
            )
        }

        fragmentViewModel.fetchData.state.observe(this, Observer {
            registerButton.isVisible = it != SingleFetchData.State.ON_REQUEST
            progressBar.isVisible = it == SingleFetchData.State.ON_REQUEST
        })
        fragmentViewModel.fetchData.data.observe(this, Observer {
            if (it.isNotEmpty()) {
                showSnackBar(getString(R.string.message_register_successfully))
            } else {
                showSnackBar(getString(R.string.message_register_failure))
            }
        })
        fragmentViewModel.fetchData.error.observe(this, Observer {
            handleError(it)
        })
    }
}

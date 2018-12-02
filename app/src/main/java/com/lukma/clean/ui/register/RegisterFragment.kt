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
import com.lukma.clean.ui.common.SingleLiveData
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<RegisterViewModel>() {
    override val resourceLayout = R.layout.fragment_register
    override val viewModel by viewModel<RegisterViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerButton.setOnClickListener {
            hideKeyboard()
            viewModel.register(
                usernameInputLayout.editText?.text.toString(),
                passwordInputLayout.editText?.text.toString(),
                fullNameInputLayout.editText?.text.toString(),
                emailInputLayout.editText?.text.toString()
            )
        }

        viewModel.registerLiveData.observe(this, Observer {
            registerButton.isVisible = it != SingleLiveData.State.ON_REQUEST
            progressBar.isVisible = it == SingleLiveData.State.ON_REQUEST

            when (it.state) {
                SingleLiveData.State.ON_REQUEST -> Unit
                SingleLiveData.State.ON_SUCCESS -> if (it.data == true) {
                    showSnackBar(getString(R.string.message_register_successfully))
                } else {
                    showSnackBar(getString(R.string.message_register_failure))
                }
                SingleLiveData.State.ON_FAILURE -> handleError(it.error)
            }
        })
    }
}

package com.lukma.clean.presentation.register

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.lukma.clean.R
import com.lukma.clean.extensions.handleError
import com.lukma.clean.extensions.hideKeyboard
import com.lukma.clean.extensions.showSnackBar
import com.lukma.clean.presentation.common.State
import com.lukma.clean.presentation.common.base.BaseFragmentVM
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragmentVM<RegisterViewModel>() {
    override val resourceLayout = R.layout.fragment_register
    override val viewModel by viewModel<RegisterViewModel>()

    override fun onInitViews() {
        registerButton.setOnClickListener {
            hideKeyboard()
            viewModel.register(
                usernameInputLayout.editText?.text.toString(),
                passwordInputLayout.editText?.text.toString(),
                fullNameInputLayout.editText?.text.toString(),
                emailInputLayout.editText?.text.toString()
            )
        }
    }

    override fun onInitObservers() {
        viewModel.registerAction.observe(this, Observer {
            registerButton.isVisible = it.state != State.ON_REQUEST
            progressBar.isVisible = it.state == State.ON_REQUEST

            when (it.state) {
                State.ON_REQUEST -> Unit
                State.ON_SUCCESS -> showSnackBar(R.string.message_register_successfully)
                State.ON_FAILURE -> handleError(it.error)
            }
        })
    }
}

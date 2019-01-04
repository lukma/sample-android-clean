package com.lukma.clean.ui.register

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.lukma.clean.R
import com.lukma.clean.extensions.handleError
import com.lukma.clean.extensions.hideKeyboard
import com.lukma.clean.extensions.showSnackBar
import com.lukma.clean.ui.common.ResourceState
import com.lukma.clean.ui.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment<RegisterViewModel>() {
    override val resourceLayout = R.layout.fragment_register
    override val viewModel by viewModel<RegisterViewModel>()

    override fun onInitViews() {
        super.onInitViews()
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
        super.onInitObservers()
        viewModel.registerLiveData.observe(this, Observer {
            registerButton.isVisible = it != ResourceState.ON_REQUEST
            progressBar.isVisible = it == ResourceState.ON_REQUEST

            when (it.state) {
                ResourceState.ON_REQUEST -> Unit
                ResourceState.ON_SUCCESS -> showSnackBar(R.string.message_register_successfully)
                ResourceState.ON_FAILURE -> handleError(it.error)
            }
        })
    }
}

package com.lukma.android.presentation.register

import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.lukma.android.R
import com.lukma.android.presentation.common.base.BaseFragment
import com.lukma.android.presentation.common.entity.Resource
import com.lukma.android.shared.extensions.handleError
import com.lukma.android.shared.extensions.hideKeyboard
import com.lukma.android.shared.extensions.showSnackBar
import kotlinx.android.synthetic.main.fragment_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : BaseFragment() {
    override val resourceLayout = R.layout.fragment_register
    private val viewModel by viewModel<RegisterViewModel>()

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
            val isLoading = it == Resource.Loading
            registerButton.isEnabled = !isLoading
            progressBar.isVisible = isLoading

            when (it) {
                is Resource.Success -> showSnackBar(R.string.message_register_successfully)
                is Resource.Failure -> handleError(it.error)
            }
        })
    }
}

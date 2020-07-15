package com.lukma.features.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.lukma.core.domain.EventState
import com.lukma.core.domain.isLoading
import com.lukma.core.uikit.closeKeyboard
import com.lukma.core.uikit.showSnackBar
import com.lukma.features.auth.R
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.login_fragment) {
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginButton.setOnClickListener {
            lifecycleScope.launch {
                closeKeyboard()
                val email = emailInput.text?.toString() ?: ""
                val password = passwordInput.text?.toString() ?: ""
                viewModel.signInWithEmail(email, password)
            }
        }
        createNewAccountButton.setOnClickListener {
            val direction = LoginFragmentDirections.actionToRegisterFragment()
            findNavController().navigate(direction)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.authResult.observe(viewLifecycleOwner) {
            loginButton.isEnabled = !it.isLoading
            when (it) {
                is EventState.Success -> {
                    val direction = LoginFragmentDirections.actionToHomeFragment()
                    findNavController().navigate(direction)
                }
                is EventState.Failure -> showSnackBar(it.error)
            }
        }
    }
}

package com.lukma.features.auth.register

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
import kotlinx.android.synthetic.main.register_fragment.*
import kotlinx.coroutines.launch

class RegisterFragment : Fragment(R.layout.register_fragment) {
    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerButton.setOnClickListener {
            lifecycleScope.launch {
                closeKeyboard()
                val email = emailInput.text?.toString() ?: ""
                val password = passwordInput.text?.toString() ?: ""
                val displayName = displayNameInput.text?.toString() ?: ""
                viewModel.signUpWithEmail(email, password, displayName)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.authResult.observe(viewLifecycleOwner) {
            registerButton.isEnabled = !it.isLoading
            when (it) {
                is EventState.Success -> showSnackBar(R.string.message_register_success) {
                    val direction = RegisterFragmentDirections.actionToHomeFragment()
                    findNavController().navigate(direction)
                }
                is EventState.Failure -> showSnackBar(it.error)
            }
        }
    }
}

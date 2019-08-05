package com.lukma.android.presentation.login

import android.content.Intent
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.lukma.android.R
import com.lukma.android.domain.auth.entity.ThirdParty
import com.lukma.android.presentation.common.base.BaseFragment
import com.lukma.android.presentation.common.entity.Resource
import com.lukma.android.presentation.main.MainActivity
import com.lukma.android.shared.extensions.handleError
import com.lukma.android.shared.extensions.hideKeyboard
import com.lukma.android.shared.extensions.startActivityClearTask
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {
    override val resourceLayout = R.layout.fragment_login
    private val viewModel by viewModel<LoginViewModel>()

    private val callbackManager by inject<CallbackManager>()
    private val loginManager by inject<LoginManager>()
    private val gso by inject<GoogleSignInOptions>()

    override fun onInitViews() {
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                viewModel.authorize(ThirdParty.FACEBOOK, loginResult.accessToken.token)
            }

            override fun onCancel() {}

            override fun onError(error: FacebookException) {
                handleError(error)
            }
        })
        loginButton.setOnClickListener {
            hideKeyboard()
            viewModel.authorize(
                usernameInputLayout.editText?.text.toString(),
                passwordInputLayout.editText?.text.toString()
            )
        }
        connectFacebookButton.setOnClickListener {
            loginManager.logInWithReadPermissions(this, listOf("email", "public_profile"))
        }
        connectGoogleButton.setOnClickListener {
            val signInIntent = (GoogleSignIn.getClient(requireContext(), gso)).signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        registerButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_registerFragment))
    }

    override fun onInitObservers() {
        viewModel.authorizeAction.observe(this, Observer(::handleAuthorizeResult))
        viewModel.authorizeByThirdPartyAction.observe(this, Observer(::handleAuthorizeResult))
    }

    private fun handleAuthorizeResult(result: Resource<Unit>) {
        val isLoading = result == Resource.Loading
        loginButton.isEnabled = !isLoading
        progressBar.isVisible = isLoading

        when (result) {
            is Resource.Success -> requireContext().startActivityClearTask(MainActivity::class.java)
            is Resource.Failure -> handleError(result.error)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.authorize(ThirdParty.GOOGLE, account?.idToken)
            } catch (e: ApiException) {
                handleError(e)
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {
        const val RC_SIGN_IN = 1
    }
}

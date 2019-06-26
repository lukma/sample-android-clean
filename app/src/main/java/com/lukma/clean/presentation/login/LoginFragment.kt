package com.lukma.clean.presentation.login

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
import com.lukma.clean.R
import com.lukma.clean.domain.auth.entity.ThirdParty
import com.lukma.clean.presentation.common.State
import com.lukma.clean.presentation.common.base.BaseFragmentVM
import com.lukma.clean.presentation.main.MainActivity
import com.lukma.clean.shared.extensions.handleError
import com.lukma.clean.shared.extensions.hideKeyboard
import com.lukma.clean.shared.extensions.startActivityClearTask
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragmentVM<LoginViewModel>() {
    override val resourceLayout = R.layout.fragment_login
    override val viewModel by viewModel<LoginViewModel>()

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
        viewModel.authorizeAction.observe(this, Observer {
            loginButton.isVisible = it.state != State.ON_REQUEST
            progressBar.isVisible = it.state == State.ON_REQUEST

            when (it.state) {
                State.ON_REQUEST -> Unit
                State.ON_SUCCESS -> context?.startActivityClearTask(MainActivity::class.java)
                State.ON_FAILURE -> handleError(it.error)
            }
        })

        viewModel.authorizeByThirdPartyAction.observe(this, Observer {
            loginButton.isVisible = it.state != State.ON_REQUEST
            progressBar.isVisible = it.state == State.ON_REQUEST

            when (it.state) {
                State.ON_REQUEST -> Unit
                State.ON_SUCCESS -> context?.startActivityClearTask(MainActivity::class.java)
                State.ON_FAILURE -> handleError(it.error)
            }
        })
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

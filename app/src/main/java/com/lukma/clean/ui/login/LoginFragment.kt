package com.lukma.clean.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
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
import com.lukma.clean.domain.common.entity.ThirdParty
import com.lukma.clean.ui.common.SingleLiveData
import com.lukma.clean.ui.common.base.BaseFragment
import com.lukma.clean.ui.common.extensions.handleError
import com.lukma.clean.ui.common.extensions.hideKeyboard
import com.lukma.clean.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<LoginViewModel>() {
    companion object {
        const val RC_SIGN_IN = 1
    }

    override val resourceLayout = R.layout.fragment_login
    override val viewModel by viewModel<LoginViewModel>()

    private val callbackManager by inject<CallbackManager>()
    private val loginManager by inject<LoginManager>()
    private val gso by inject<GoogleSignInOptions>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                viewModel.authorize(ThirdParty.FACEBOOK, loginResult.accessToken.token)
            }

            override fun onCancel() {}

            override fun onError(error: FacebookException) {
                handleError(error)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        connectGoogleButton.setOnClickListener { _ ->
            context?.let {
                val signInIntent = (GoogleSignIn.getClient(it, gso)).signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        }
        registerButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_registerFragment))

        viewModel.authorizeByUsernameOrEmailLiveData.observe(this, Observer {
            loginButton.isVisible = it != SingleLiveData.State.ON_REQUEST
            progressBar.isVisible = it == SingleLiveData.State.ON_REQUEST

            when (it.state) {
                SingleLiveData.State.ON_REQUEST -> Unit
                SingleLiveData.State.ON_SUCCESS -> startActivity(
                    Intent(context, MainActivity::class.java).addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    )
                )
                SingleLiveData.State.ON_FAILURE -> handleError(it.error)
            }
        })

        viewModel.authorizeByThirdPartyLiveData.observe(this, Observer {
            loginButton.isVisible = it != SingleLiveData.State.ON_REQUEST
            progressBar.isVisible = it == SingleLiveData.State.ON_REQUEST

            when (it.state) {
                SingleLiveData.State.ON_REQUEST -> Unit
                SingleLiveData.State.ON_SUCCESS -> startActivity(
                    Intent(context, MainActivity::class.java).addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    )
                )
                SingleLiveData.State.ON_FAILURE -> handleError(it.error)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

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
}

package com.lukma.clean.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.lukma.clean.R
import com.lukma.clean.extensions.handleError
import com.lukma.clean.extensions.hideKeyboard
import com.lukma.clean.ui.base.BaseFragment
import com.lukma.clean.ui.common.SingleFetchData
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment<LoginViewModel>() {
    companion object {
        const val RC_SIGN_IN = 1
    }

    override val resourceLayout = R.layout.fragment_login
    override val fragmentViewModel by viewModel<LoginViewModel>()

    private val callbackManager by inject<CallbackManager>()
    private val loginManager by inject<LoginManager>()
    private val gso by inject<GoogleSignInOptions>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                fragmentViewModel.signIn(loginResult.accessToken)
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
            fragmentViewModel.signIn(emailEditText.text.toString(), passwordEditText.text.toString())
        }
        connectFacebookButton.setOnClickListener {
            loginManager.logInWithReadPermissions(this, listOf("email", "public_profile"))
        }
        connectGoogleButton.setOnClickListener {
            context?.let { context ->
                val signInIntent = (GoogleSignIn.getClient(context, gso)).signInIntent
                startActivityForResult(signInIntent, RC_SIGN_IN)
            }
        }
        registerButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_registerFragment)
        }

        fragmentViewModel.signInWithEmailFetchData.state.observe(this, Observer {
            loginButton.isVisible = it != SingleFetchData.State.ON_REQUEST
            progressBar.isVisible = it == SingleFetchData.State.ON_REQUEST
        })
        fragmentViewModel.signInWithEmailFetchData.data.observe(this, Observer {
            fragmentViewModel.authorize(it.user.uid)
        })
        fragmentViewModel.signInWithEmailFetchData.error.observe(this, Observer {
            handleError(it)
        })

        fragmentViewModel.signInWithFacebookFetchData.state.observe(this, Observer {
            loginButton.isVisible = it != SingleFetchData.State.ON_REQUEST
            progressBar.isVisible = it == SingleFetchData.State.ON_REQUEST
        })
        fragmentViewModel.signInWithFacebookFetchData.data.observe(this, Observer {
            if (it.additionalUserInfo.isNewUser) {
                fragmentViewModel.register(it.user.uid)
            } else {
                fragmentViewModel.authorize(it.user.uid)
            }
        })
        fragmentViewModel.signInWithFacebookFetchData.error.observe(this, Observer {
            handleError(it)
        })

        fragmentViewModel.signInWithGoogleFetchData.state.observe(this, Observer {
            loginButton.isVisible = it != SingleFetchData.State.ON_REQUEST
            progressBar.isVisible = it == SingleFetchData.State.ON_REQUEST
        })
        fragmentViewModel.signInWithGoogleFetchData.data.observe(this, Observer {
            if (it.additionalUserInfo.isNewUser) {
                fragmentViewModel.register(it.user.uid)
            } else {
                fragmentViewModel.authorize(it.user.uid)
            }
        })
        fragmentViewModel.signInWithGoogleFetchData.error.observe(this, Observer {
            handleError(it)
        })

        fragmentViewModel.registerFetchData.state.observe(this, Observer {
            loginButton.isVisible = it != SingleFetchData.State.ON_REQUEST
            progressBar.isVisible = it == SingleFetchData.State.ON_REQUEST
        })
        fragmentViewModel.registerFetchData.data.observe(this, Observer {
            fragmentViewModel.authorize(it)
        })
        fragmentViewModel.registerFetchData.error.observe(this, Observer {
            handleError(it)
        })

        fragmentViewModel.authorizeFetchData.state.observe(this, Observer {
            loginButton.isVisible = it != SingleFetchData.State.ON_REQUEST
            progressBar.isVisible = it == SingleFetchData.State.ON_REQUEST
        })
        fragmentViewModel.authorizeFetchData.data.observe(this, Observer {
            if (it) {
                activity?.finish()
                view.findNavController().navigate(R.id.action_mainActivity)
            }
        })
        fragmentViewModel.authorizeFetchData.error.observe(this, Observer {
            handleError(it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                fragmentViewModel.signIn(account)
            } catch (e: ApiException) {
                handleError(e)
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }
}

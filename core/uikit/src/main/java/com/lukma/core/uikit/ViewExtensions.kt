package com.lukma.core.uikit

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun Fragment.closeKeyboard() {
    val imm =
        requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
}

fun Fragment.showSnackBar(message: String, dismissAction: () -> Unit = {}) {
    Snackbar
        .make(requireView(), message, Snackbar.LENGTH_SHORT)
        .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                dismissAction()
            }
        })
        .show()
}

fun Fragment.showSnackBar(@StringRes messageResId: Int, delayingAction: () -> Unit = {}) {
    showSnackBar(getString(messageResId), delayingAction)
}

fun Fragment.showSnackBar(error: Throwable, delayingAction: () -> Unit = {}) {
    showSnackBar(
        error.localizedMessage ?: getString(R.string.message_error_general),
        delayingAction
    )
}

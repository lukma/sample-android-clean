package com.lukma.clean.extensions

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.hideKeyboard() {
    val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
}

fun Fragment.handleError(error: Throwable?) {
    showSnackBar(error?.localizedMessage.orEmpty())
}

fun Fragment.showSnackBar(message: String) {
    activity?.let {
        Snackbar.make(it.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
}

fun Fragment.showSnackBar(resource: Int) {
    showSnackBar(getString(resource))
}

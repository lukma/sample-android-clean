package com.lukma.android.shared.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.hideKeyboard() {
    val imm =
        requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
}

fun Fragment.showSnackBar(message: String) {
    val view = requireActivity().findViewById<View>(android.R.id.content)
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.showSnackBar(resource: Int) {
    showSnackBar(getString(resource))
}

fun Fragment.handleError(error: Throwable) {
    showSnackBar(error.localizedMessage)
}

package com.lukma.clean.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.core.app.TaskStackBuilder
import androidx.fragment.app.Fragment
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.get
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

fun NavController.navigateClearTask(
    context: Context?,
    navDirections: NavDirections,
    flags: Int = 0
) {
    val destinationId = graph.getAction(navDirections.actionId)?.destinationId
    val destination = destinationId?.let(graph::get)
    if (destination is ActivityNavigator.Destination) {
        destination.intent?.let {
            context?.let(TaskStackBuilder::create)?.addNextIntentWithParentStack(it.addFlags(flags))
                ?.startActivities()
        }
    } else throw IllegalArgumentException("The destination of the direction is not an activity ")
}

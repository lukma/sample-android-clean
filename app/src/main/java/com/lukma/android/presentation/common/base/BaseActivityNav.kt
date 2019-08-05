package com.lukma.android.presentation.common.base

import androidx.navigation.fragment.NavHostFragment

abstract class BaseActivityNav : BaseActivity() {
    abstract val navHost: NavHostFragment?
    protected val navController by lazy { navHost?.let(NavHostFragment::findNavController) }

    override fun onSupportNavigateUp() = navController?.navigateUp() ?: super.onSupportNavigateUp()
}

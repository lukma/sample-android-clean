package com.lukma.android.presentation.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

abstract class BaseFragment : Fragment() {
    abstract val resourceLayout: Int

    init {
        lifecycleScope.launchWhenCreated {
            onInitViews()
            onInitObservers()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(resourceLayout, container, false)

    protected open fun onInitViews() {}

    protected open fun onInitObservers() {}
}

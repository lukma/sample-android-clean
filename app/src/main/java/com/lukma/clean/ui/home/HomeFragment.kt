package com.lukma.clean.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukma.clean.R
import com.lukma.clean.extensions.handleError
import com.lukma.clean.ui.base.BaseFragment
import com.lukma.clean.ui.common.PagedFetchData
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewModel>() {
    override val resourceLayout = R.layout.fragment_home
    override val fragmentViewModel by viewModel<HomeViewModel>()

    private val recyclerAdapter by lazy { ContentAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
        swipeRefresh.setOnRefreshListener {
            fragmentViewModel.fetchData.reload()
        }

        fragmentViewModel.fetchData.state.observe(this, Observer {
            swipeRefresh.isRefreshing = it == PagedFetchData.State.ON_FIRST_REQUEST
            recyclerAdapter.currentState = it
        })
        fragmentViewModel.fetchData.data.observe(this, Observer {
            recyclerAdapter.submitList(it)
        })
        fragmentViewModel.fetchData.error.observe(this, Observer {
            handleError(it)
        })
    }
}
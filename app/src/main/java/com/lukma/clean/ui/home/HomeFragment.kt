package com.lukma.clean.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukma.clean.R
import com.lukma.clean.extensions.handleError
import com.lukma.clean.ui.base.BaseFragment
import com.lukma.clean.ui.common.PagedLiveData
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewModel>() {
    override val resourceLayout = R.layout.fragment_home
    override val viewModel by viewModel<HomeViewModel>()

    private val recyclerAdapter by lazy { ContentAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
        swipeRefresh.setOnRefreshListener {
            viewModel.liveData.reload()
        }

        viewModel.liveData.state.observe(this, Observer {
            swipeRefresh.isRefreshing = it == PagedLiveData.State.ON_FIRST_REQUEST
            recyclerAdapter.currentState = it
        })
        viewModel.liveData.data.observe(this, Observer {
            recyclerAdapter.submitList(it)
        })
        viewModel.liveData.error.observe(this, Observer(this::handleError))
    }
}
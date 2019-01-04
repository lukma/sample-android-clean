package com.lukma.clean.ui.home

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukma.clean.R
import com.lukma.clean.extensions.handleError
import com.lukma.clean.ui.common.PagedLiveData
import com.lukma.clean.ui.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewModel>() {
    override val resourceLayout = R.layout.fragment_home
    override val viewModel by viewModel<HomeViewModel>()

    private val recyclerAdapter by lazy { ContentAdapter() }

    override fun onInitViews() {
        super.onInitViews()
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
        swipeRefresh.setOnRefreshListener {
            viewModel.liveData.reload()
        }
    }

    override fun onInitObservers() {
        super.onInitObservers()
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

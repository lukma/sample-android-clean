package com.lukma.clean.ui.home

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukma.clean.R
import com.lukma.clean.extensions.handleError
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
            viewModel.getListOfContentBuilder.reload()
        }
    }

    override fun onInitObservers() {
        super.onInitObservers()
        viewModel.getListOfContentBuilder.state.observe(this, Observer {
            recyclerAdapter.currentState = it
        })
        viewModel.getListOfContentBuilder.data.observe(this, Observer {
            swipeRefresh.isRefreshing = false
            recyclerAdapter.submitList(it)
        })
        viewModel.getListOfContentBuilder.error.observe(this, Observer(this::handleError))
    }
}

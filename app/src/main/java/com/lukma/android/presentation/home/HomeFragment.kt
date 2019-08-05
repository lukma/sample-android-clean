package com.lukma.android.presentation.home

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukma.android.R
import com.lukma.android.presentation.common.base.BaseFragment
import com.lukma.android.presentation.common.widget.recycler.PagedState
import com.lukma.android.shared.extensions.handleError
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {
    override val resourceLayout = R.layout.fragment_home
    private val viewModel by viewModel<HomeViewModel>()

    private val contentListAdapter = ContentListAdapter {}

    override fun onInitViews() {
        with(recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = contentListAdapter
        }
        swipeRefresh.setOnRefreshListener {
            viewModel.contents.value?.dataSource?.invalidate()
        }
    }

    override fun onInitObservers() {
        viewModel.networkState.observe(this, Observer {
            contentListAdapter.currentState = it
            swipeRefresh.isRefreshing = it is PagedState.Loading && it.isInitial
            if (it is PagedState.Failure && it.isInitial) handleError(it.error)
        })
        viewModel.contents.observe(this, Observer(contentListAdapter::submitList))
    }
}

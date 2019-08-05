package com.lukma.android.presentation.home

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukma.android.R
import com.lukma.android.presentation.common.PagedState
import com.lukma.android.presentation.common.base.BaseFragment
import com.lukma.android.shared.extensions.handleError
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {
    override val resourceLayout = R.layout.fragment_home
    private val viewModel by viewModel<HomeViewModel>()

    private val contentListAdapter by lazy { ContentListAdapter() }

    override fun onInitViews() {
        with(recyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = contentListAdapter
        }
        swipeRefresh.setOnRefreshListener {
            viewModel.reload()
        }
    }

    override fun onInitObservers() {
        viewModel.networkState.observe(this, Observer {
            contentListAdapter.currentState = it.state
            swipeRefresh.isRefreshing = it.state == PagedState.ON_INITIAL_REQUEST
            when (it.state) {
                PagedState.ON_INITIAL_REQUEST -> Unit
                PagedState.ON_FAILURE -> handleError(it.error)
                else -> swipeRefresh.isRefreshing = false
            }
        })
        viewModel.listOfContent.observe(this, Observer(contentListAdapter::submitList))
    }
}

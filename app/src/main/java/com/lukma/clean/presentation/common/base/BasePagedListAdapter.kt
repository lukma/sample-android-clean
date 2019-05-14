package com.lukma.clean.presentation.common.base

import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukma.clean.presentation.common.PagedState
import com.lukma.clean.presentation.common.ProgressHolder

abstract class BasePagedListAdapter<Entity, ItemHolder : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<Entity>
) : PagedListAdapter<Entity, RecyclerView.ViewHolder>(diffCallback) {

    internal var currentState = PagedState.NONE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_PROGRESS -> onCreateProgressHolder(parent)
            TYPE_ITEM -> onCreateItemHolder(parent, viewType)
            else -> throw IllegalStateException("View type not know")
        }

    @CallSuper
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == TYPE_ITEM) {
            @Suppress("UNCHECKED_CAST")
            onBindItemHolder(holder as ItemHolder, position)
        }
    }

    override fun getItemCount() = super.getItemCount() + if (isOnLoad()) 1 else 0

    @CallSuper
    override fun getItemViewType(position: Int) = if (isOnLoad() && position == itemCount - 1)
        TYPE_PROGRESS else TYPE_ITEM

    protected fun onCreateProgressHolder(parent: ViewGroup) = ProgressHolder.newInstance(parent)

    abstract fun onCreateItemHolder(parent: ViewGroup, viewType: Int): ItemHolder

    abstract fun onBindItemHolder(holder: ItemHolder, position: Int): Unit?

    private fun isOnLoad() = currentState == PagedState.ON_NEXT_REQUEST

    companion object {
        private const val TYPE_PROGRESS = 0
        private const val TYPE_ITEM = 1
    }
}

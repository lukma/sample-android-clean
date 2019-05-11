package com.lukma.clean.ui.common.base

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukma.clean.ui.common.PagedBuilder
import com.lukma.clean.ui.common.ProgressHolder

abstract class BasePagedAdapter<Entity, ItemHolder : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<Entity>
) : PagedListAdapter<Entity, RecyclerView.ViewHolder>(diffCallback) {
    internal var currentState = PagedBuilder.State.NONE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        TYPE_PROGRESS -> ProgressHolder.newInstance(parent)
        else -> onCreateItemHolder(parent, viewType)
    }

    abstract fun onCreateItemHolder(parent: ViewGroup, viewType: Int): ItemHolder

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == TYPE_ITEM) {
            onBindItemHolder(holder as ItemHolder, position)
        }
    }

    abstract fun onBindItemHolder(holder: ItemHolder, position: Int): Unit?

    override fun getItemCount() = super.getItemCount() + if (isOnLoad()) 1 else 0

    override fun getItemViewType(position: Int) = if (isOnLoad() && position == itemCount - 1)
        TYPE_PROGRESS else TYPE_ITEM

    private fun isOnLoad() = currentState == PagedBuilder.State.ON_NEXT_REQUEST

    companion object {
        private const val TYPE_PROGRESS = 0
        private const val TYPE_ITEM = 1
    }
}

package com.lukma.clean.ui.common.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukma.clean.R
import com.lukma.clean.ui.common.PagedLiveData

abstract class BasePagedAdapter<Entity, ItemHolder : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<Entity>
) : PagedListAdapter<Entity, RecyclerView.ViewHolder>(diffCallback) {
    companion object {
        private const val TYPE_PROGRESS = 0
        private const val TYPE_ITEM = 1
    }

    var currentState = PagedLiveData.State.NONE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        TYPE_PROGRESS -> ProgressHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_loading, parent, false))
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

    override fun getItemCount() = super.getItemCount() + if (hasFooter()) 1 else 0

    override fun getItemViewType(position: Int) = if (hasFooter() && position == itemCount - 1)
        TYPE_PROGRESS else TYPE_ITEM

    private fun hasFooter() = currentState == PagedLiveData.State.ON_NEXT_REQUEST

    class ProgressHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

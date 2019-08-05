package com.lukma.android.presentation.common.widget.recycler

import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class AppPagedListAdapter<Entity, ItemHolder : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<Entity>
) : PagedListAdapter<Entity, RecyclerView.ViewHolder>(diffCallback) {

    var currentState: PagedState =
        PagedState.Loading(true)
        set(value) {
            val prevState = currentState
            val prevExtraRow = hasExtraRow()
            field = value
            val newExtraRow = hasExtraRow()
            if (prevExtraRow != newExtraRow) {
                if (prevExtraRow) {
                    notifyItemRemoved(itemCount)
                } else {
                    notifyItemInserted(itemCount)
                }
            } else if (newExtraRow && prevState != value) {
                notifyItemChanged(itemCount - 1)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_PROGRESS -> onCreateProgressHolder(parent)
            TYPE_ITEM -> onCreateItemHolder(parent, viewType)
            else -> throw IllegalStateException("View type not know")
        }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun onCreateProgressHolder(parent: ViewGroup) =
        ProgressHolder.newInstance(
            parent
        )

    abstract fun onCreateItemHolder(parent: ViewGroup, viewType: Int): ItemHolder

    @CallSuper
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == TYPE_ITEM) {
            @Suppress("UNCHECKED_CAST")
            onBindItemHolder(holder as ItemHolder, position)
        }
    }

    abstract fun onBindItemHolder(holder: ItemHolder, position: Int): Unit?

    @CallSuper
    override fun getItemCount(): Int = super.getItemCount() + if (hasExtraRow()) 1 else 0

    @CallSuper
    override fun getItemViewType(position: Int) = if (hasExtraRow() && position == itemCount - 1) {
        TYPE_PROGRESS
    } else {
        TYPE_ITEM
    }

    @Suppress("USELESS_IS_CHECK")
    private fun hasExtraRow() = currentState is PagedState.Loading && !currentState.isInitial

    companion object {
        private const val TYPE_PROGRESS = 0
        private const val TYPE_ITEM = 1
    }
}

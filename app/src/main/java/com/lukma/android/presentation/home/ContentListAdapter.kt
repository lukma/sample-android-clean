package com.lukma.android.presentation.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.lukma.android.domain.content.entity.Content
import com.lukma.android.presentation.common.widget.recycler.AppPagedListAdapter

class ContentListAdapter(private val onItemClicked: (Content) -> Unit) :
    AppPagedListAdapter<Content, ContentHolder>(DIFF_CALLBACK) {

    override fun onCreateItemHolder(parent: ViewGroup, viewType: Int): ContentHolder =
        ContentHolder.newInstance(parent, onItemClicked)

    override fun onBindItemHolder(holder: ContentHolder, position: Int) {
        getItem(position)?.run(holder::onBind)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(oldItem: Content, newItem: Content): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Content, newItem: Content) = oldItem == newItem
        }
    }
}

package com.lukma.clean.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.lukma.clean.domain.content.entity.Content
import com.lukma.clean.ui.common.base.BasePagedAdapter

class ContentAdapter(private val onClickItemListener: (Content) -> Unit = {}) :
    BasePagedAdapter<Content, ContentHolder>(DIFF_CALLBACK) {

    override fun onCreateItemHolder(parent: ViewGroup, viewType: Int) = ContentHolder
        .newInstance(parent, onClickItemListener)

    override fun onBindItemHolder(holder: ContentHolder, position: Int) = getItem(position)
        ?.let(holder::onBind)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(oldItem: Content, newItem: Content) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Content, newItem: Content) = oldItem == newItem
        }
    }
}

package com.lukma.clean.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukma.clean.R
import com.lukma.clean.domain.content.Content
import com.lukma.clean.ui.base.BasePagedAdapter
import com.lukma.clean.ui.common.GlideApp
import kotlinx.android.synthetic.main.item_content.view.*

class ContentAdapter(
    private val onClickItemListener: (Content?) -> Unit = {}
) : BasePagedAdapter<Content, ContentAdapter.ContentHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(oldItem: Content, newItem: Content) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Content, newItem: Content) = oldItem == newItem
        }
    }

    override fun onCreateItemHolder(parent: ViewGroup, viewType: Int) =
        ContentHolder.newInstance(parent)

    override fun onBindItemHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as ContentHolder).bind(getItem(position), onClickItemListener)

    class ContentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun newInstance(parent: ViewGroup) = ContentHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_content, parent, false))
        }

        fun bind(item: Content?, onClickItemListener: (Content?) -> Unit) {
            GlideApp.with(itemView).load(item?.thumbnail).into(itemView.thumbnailImageView)
            itemView.contentTextView.text = item?.content
            itemView.setOnClickListener { onClickItemListener(item) }
        }
    }
}
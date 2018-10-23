package com.lukma.clean.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukma.clean.R
import com.lukma.clean.data.content.Content
import com.lukma.clean.ui.base.BasePagedAdapter
import com.lukma.clean.ui.common.GlideApp

class ContentAdapter(private val onClickItemListener: (Content?) -> Unit = {}) :
        BasePagedAdapter<Content, ContentAdapter.ContentHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(oldItem: Content, newItem: Content) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Content, newItem: Content) = oldItem == newItem
        }
    }

    override fun onCreateItemHolder(parent: ViewGroup, viewType: Int) = ContentHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_content, parent, false))

    override fun onBindItemHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ContentHolder).bind(getItem(position), onClickItemListener)
    }

    class ContentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val thumbnailImageView: ImageView = itemView.findViewById(R.id.thumbnailImageView)
        private val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)

        fun bind(item: Content?, onClickItemListener: (Content?) -> Unit) {
            GlideApp.with(itemView).load(item?.thumbnail).into(thumbnailImageView)
            contentTextView.text = item?.content
            itemView.setOnClickListener { onClickItemListener(item) }
        }
    }
}
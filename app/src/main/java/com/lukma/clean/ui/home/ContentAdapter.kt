package com.lukma.clean.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukma.clean.R
import com.lukma.clean.domain.content.Content
import com.lukma.clean.ui.common.GlideApp
import com.lukma.clean.ui.common.base.BasePagedAdapter
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
        ContentHolder.newInstance(parent, onClickItemListener)

    override fun onBindItemHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as ContentHolder).bind(getItem(position))

    class ContentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        companion object {
            fun newInstance(
                parent: ViewGroup,
                onClickItemListener: (Content?) -> Unit
            ): ContentHolder {
                val holder = ContentHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_content, parent, false))
                holder.onCreate(onClickItemListener)
                return holder
            }
        }

        private var item: Content? = null

        fun onCreate(onClickItemListener: (Content?) -> Unit) {
            itemView.setOnClickListener { onClickItemListener(item) }
        }

        fun bind(item: Content?) {
            this.item = item
            GlideApp.with(itemView).load(item?.thumbnail).into(itemView.thumbnailImageView)
            itemView.contentTextView.text = item?.content
        }
    }
}
package com.lukma.clean.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukma.clean.R
import com.lukma.clean.domain.content.Content
import com.lukma.clean.ui.common.GlideApp
import kotlinx.android.synthetic.main.item_content.view.*

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

    private val thumbnailImageView = itemView.thumbnailImageView
    private val contentTextView = itemView.contentTextView

    fun onCreate(onClickItemListener: (Content?) -> Unit) {
        itemView.setOnClickListener { onClickItemListener(item) }
    }

    fun onBind(item: Content?) {
        this.item = item
        GlideApp.with(itemView).load(item?.thumbnail).into(thumbnailImageView)
        contentTextView.text = item?.content
    }
}
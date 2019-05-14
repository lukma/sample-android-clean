package com.lukma.clean.presentation.home

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lukma.clean.R
import com.lukma.clean.domain.content.entity.Content
import com.lukma.clean.extensions.inflate
import com.lukma.clean.presentation.common.module.GlideApp
import kotlinx.android.synthetic.main.item_content.view.*

class ContentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var item: Content? = null

    private val thumbnailImageView = itemView.thumbnailImageView
    private val contentTextView = itemView.contentTextView
    private val progressBar = itemView.progressBar

    fun onCreate(onClickItemListener: (Content) -> Unit) {
        itemView.setOnClickListener { item?.let(onClickItemListener) }
    }

    fun onBind(item: Content) {
        this.item = item
        GlideApp.with(itemView)
            .load(item.thumbnail)
            .error(R.drawable.ic_broken_image_black_24dp)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.isVisible = false
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.isVisible = false
                    return false
                }

            })
            .into(thumbnailImageView)
        contentTextView.text = item.content
    }

    companion object {
        fun newInstance(parent: ViewGroup, onClickItemListener: (Content) -> Unit) =
            ContentHolder(parent.inflate(R.layout.item_content)).apply {
                onCreate(onClickItemListener)
            }
    }
}

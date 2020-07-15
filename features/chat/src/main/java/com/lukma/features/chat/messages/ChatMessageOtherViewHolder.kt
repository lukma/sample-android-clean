package com.lukma.features.chat.messages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukma.core.domain.chat.ChatMessage
import com.lukma.features.chat.R
import kotlinx.android.synthetic.main.chat_message_other_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class ChatMessageOtherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val contentText = itemView.contentText
    private val createdAtText = itemView.createdAtText

    fun bind(message: ChatMessage) {
        contentText.text = message.content
        val createdAt = SimpleDateFormat("HH:mm", Locale.getDefault())
            .format(message.createdAt)
        createdAtText.text = createdAt
    }

    companion object {
        fun newInstance(parent: ViewGroup): ChatMessageOtherViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.chat_message_other_item, parent, false)
            return ChatMessageOtherViewHolder(view)
        }
    }
}

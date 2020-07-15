package com.lukma.features.chat.rooms

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukma.core.domain.chat.ChatRoom
import com.lukma.features.chat.R
import kotlinx.android.synthetic.main.chat_room_item.view.*

class ChatRoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val contentText = itemView.contentText
    private val createdAtText = itemView.createdAtText

    private lateinit var room: ChatRoom
    private lateinit var onOpenMessages: (String) -> Unit

    init {
        itemView.setOnClickListener {
            onOpenMessages.invoke(room.id)
        }
    }

    fun bind(room: ChatRoom) {
        this.room = room
        contentText.text = room.lastMessage.content
        val now = System.currentTimeMillis()
        createdAtText.text = DateUtils.getRelativeTimeSpanString(
            room.lastMessage.createdAt.time,
            now,
            DateUtils.MINUTE_IN_MILLIS
        )
    }

    companion object {
        fun newInstance(parent: ViewGroup, onOpenMessages: (String) -> Unit): ChatRoomViewHolder {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.chat_room_item, parent, false)
            return ChatRoomViewHolder(view).also {
                it.onOpenMessages = onOpenMessages
            }
        }
    }
}

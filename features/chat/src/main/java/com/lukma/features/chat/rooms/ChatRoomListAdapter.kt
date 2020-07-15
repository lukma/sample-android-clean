package com.lukma.features.chat.rooms

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukma.core.domain.chat.ChatRoom

class ChatRoomListAdapter(
    private val onOpenMessages: (String) -> Unit
) : RecyclerView.Adapter<ChatRoomViewHolder>() {
    var rooms: List<ChatRoom> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {
        return ChatRoomViewHolder.newInstance(parent, onOpenMessages)
    }

    override fun getItemCount(): Int = rooms.size

    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        holder.bind(rooms[position])
    }
}

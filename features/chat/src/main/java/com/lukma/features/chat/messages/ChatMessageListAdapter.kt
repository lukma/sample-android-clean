package com.lukma.features.chat.messages

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukma.core.domain.chat.ChatMessage
import com.lukma.core.domain.user.User
import kotlinx.coroutines.runBlocking

class ChatMessageListAdapter(
    private val isCurrentUser: suspend (User) -> Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var messages: List<ChatMessage> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MessageType.ME.ordinal -> ChatMessageMeViewHolder.newInstance(parent)
            MessageType.OTHER.ordinal -> ChatMessageOtherViewHolder.newInstance(parent)
            else -> throw IllegalStateException("Unknown view type")
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChatMessageMeViewHolder -> holder.bind(messages[position])
            is ChatMessageOtherViewHolder -> holder.bind(messages[position])
        }
    }

    override fun getItemViewType(position: Int): Int = runBlocking {
        if (isCurrentUser(messages[position].createdBy)) {
            MessageType.ME
        } else {
            MessageType.OTHER
        }
    }.ordinal

    enum class MessageType {
        ME,
        OTHER
    }
}

package com.lukma.core.domain.chat

data class ChatRoom(
    val id: String,
    val members: List<String>,
    val lastMessage: ChatMessage
)

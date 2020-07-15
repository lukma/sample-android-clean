package com.lukma.core.domain.chat

import com.lukma.core.domain.user.User
import java.util.*

data class ChatMessage(
    val content: String,
    val createdBy: User,
    val createdAt: Date
)

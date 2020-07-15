package com.lukma.core.domain.chat

sealed class SendMessageAction(open val content: String) {
    data class New(val to: String, override val content: String) : SendMessageAction(content)
    data class Reply(val roomId: String, override val content: String) : SendMessageAction(content)
}

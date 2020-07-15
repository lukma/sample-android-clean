package com.lukma.core.data.chat.cloud

import com.lukma.core.domain.chat.ChatRoom

data class ChatRoomData(
    var members: List<String>? = null,
    var lastMessage: ChatMessageData? = null
) {

    fun toEntity(withId: String) = ChatRoom(
        id = withId,
        members = members ?: throw NoSuchElementException(),
        lastMessage = lastMessage?.toEntity() ?: throw NoSuchElementException()
    )
}

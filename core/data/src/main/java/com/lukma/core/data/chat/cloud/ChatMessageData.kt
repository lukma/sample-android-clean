package com.lukma.core.data.chat.cloud

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import com.lukma.core.domain.chat.ChatMessage
import com.lukma.core.domain.user.User

data class ChatMessageData(
    var roomId: String? = null,
    var content: String? = null,
    var createdBy: CreatedBBy? = null,
    @ServerTimestamp
    var createdAt: Timestamp? = null
) {

    data class CreatedBBy(
        val email: String? = null,
        val displayName: String? = null
    )

    fun toEntity() = ChatMessage(
        content = content ?: throw NoSuchElementException(),
        createdBy = User(
            email = createdBy?.email ?: throw NoSuchElementException(),
            displayName = createdBy?.displayName ?: throw NoSuchElementException()
        ),
        createdAt = createdAt?.toDate() ?: throw NoSuchElementException()
    )
}

package com.lukma.core.domain.chat

import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun getChatRooms(): Flow<List<ChatRoom>>
    suspend fun getChatRoomByMembers(members: List<String>): ChatRoom?
    suspend fun getChatMessages(roomId: String): Flow<List<ChatMessage>>
    suspend fun sendMessage(action: SendMessageAction)
}

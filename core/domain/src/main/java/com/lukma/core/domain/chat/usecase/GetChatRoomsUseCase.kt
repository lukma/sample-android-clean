package com.lukma.core.domain.chat.usecase

import com.lukma.core.domain.BaseUseCase
import com.lukma.core.domain.chat.ChatRepository
import com.lukma.core.domain.chat.ChatRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class GetChatRoomsUseCase(
    private val chatRepository: ChatRepository
) : BaseUseCase<Flow<List<ChatRoom>>>() {

    override val coroutineContext: CoroutineContext = Dispatchers.IO

    override suspend fun build() = chatRepository.getChatRooms()
}

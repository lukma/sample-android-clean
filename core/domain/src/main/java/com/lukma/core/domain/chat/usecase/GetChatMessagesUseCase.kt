package com.lukma.core.domain.chat.usecase

import com.lukma.core.domain.BaseUseCase
import com.lukma.core.domain.chat.ChatMessage
import com.lukma.core.domain.chat.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class GetChatMessagesUseCase(
    private val chatRepository: ChatRepository
) : BaseUseCase<Flow<List<ChatMessage>>>() {

    override val coroutineContext: CoroutineContext = Dispatchers.IO

    fun addParams(roomId: String) = apply {
        val params = mapOf(
            KEY_ROOM_ID to roomId
        )
        addParams(params)
    }

    override suspend fun build() = chatRepository.getChatMessages(params[KEY_ROOM_ID] as String)

    companion object {
        private const val KEY_ROOM_ID = "KEY_ROOM_ID"
    }
}

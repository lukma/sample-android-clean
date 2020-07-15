package com.lukma.core.domain.chat.usecase

import com.lukma.core.domain.BaseUseCase
import com.lukma.core.domain.chat.ChatRepository
import com.lukma.core.domain.chat.ChatRoom
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class GetChatRoomByMembersUseCase(
    private val chatRepository: ChatRepository
) : BaseUseCase<ChatRoom?>() {

    override val coroutineContext: CoroutineContext = Dispatchers.IO

    fun addParams(members: List<String>) = apply {
        val params = mapOf(
            KEY_MEMBERS to members
        )
        addParams(params)
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun build() = chatRepository.getChatRoomByMembers(
        params[KEY_MEMBERS] as List<String>
    )

    companion object {
        private const val KEY_MEMBERS = "KEY_MEMBERS"
    }
}

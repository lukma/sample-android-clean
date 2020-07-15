package com.lukma.core.domain.chat.usecase

import com.lukma.core.domain.BaseUseCase
import com.lukma.core.domain.chat.ChatRepository
import com.lukma.core.domain.chat.SendMessageAction
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class SendChatMessageUseCase(private val chatRepository: ChatRepository) : BaseUseCase<Unit>() {
    override val coroutineContext: CoroutineContext = Dispatchers.IO

    fun addParams(action: SendMessageAction) = apply {
        val params = mapOf(KEY_ACTION to action)
        addParams(params)
    }

    override suspend fun build() = chatRepository.sendMessage(
        params[KEY_ACTION] as SendMessageAction
    )

    companion object {
        private const val KEY_ACTION = "KEY_ACTION"
    }
}

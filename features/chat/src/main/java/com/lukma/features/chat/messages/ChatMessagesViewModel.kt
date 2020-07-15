package com.lukma.features.chat.messages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lukma.core.domain.EventState
import com.lukma.core.domain.account.Profile
import com.lukma.core.domain.account.usecase.GetProfileUseCase
import com.lukma.core.domain.chat.ChatMessage
import com.lukma.core.domain.chat.ChatRoom
import com.lukma.core.domain.chat.SendMessageAction
import com.lukma.core.domain.chat.usecase.GetChatMessagesUseCase
import com.lukma.core.domain.chat.usecase.GetChatRoomByMembersUseCase
import com.lukma.core.domain.chat.usecase.SendChatMessageUseCase
import com.lukma.core.domain.eventState
import com.lukma.core.domain.getOrNull
import com.lukma.core.domain.user.User
import kotlinx.coroutines.flow.collect
import org.koin.core.KoinComponent
import org.koin.core.inject

class ChatMessagesViewModel : ViewModel(), KoinComponent {
    private val getProfileUseCase: GetProfileUseCase by inject()
    private val getChatRoomByMembersUseCase: GetChatRoomByMembersUseCase by inject()
    private val getChatMessagesUseCase: GetChatMessagesUseCase by inject()
    private val sendChatMessageUseCase: SendChatMessageUseCase by inject()

    private var profile: Profile? = null

    private val roomMutable = MutableLiveData<ChatRoom>()
    internal val room: LiveData<ChatRoom> = roomMutable

    private val messagesMutable = MutableLiveData<List<ChatMessage>>()
    internal val messages: LiveData<List<ChatMessage>> = messagesMutable

    private val sendMessageResultMutable = MutableLiveData<EventState<Unit>>()
    internal val sendMessageResult: LiveData<EventState<Unit>> = sendMessageResultMutable

    var roomId: String? = null
        private set

    suspend fun isCurrentUser(compare: User): Boolean {
        if (profile == null) {
            profile = getProfileUseCase.invoke().getOrNull() ?: return false
        }
        return compare.email == profile?.email
    }

    suspend fun getRoom(members: List<String>) {
        val result = getChatRoomByMembersUseCase
            .addParams(members)
            .invoke()
            .getOrNull()
            ?: return
        this.roomId = result.id
        roomMutable.postValue(result)
    }

    suspend fun getMessages(roomId: String) {
        this.roomId = roomId
        getChatMessagesUseCase
            .addParams(roomId)
            .invoke()
            .getOrNull()
            ?.collect { messagesMutable.postValue(it) }
    }

    suspend fun sendMessage(action: SendMessageAction) {
        sendMessageResultMutable.value = EventState.Loading
        val result = sendChatMessageUseCase
            .addParams(action)
            .invoke()
            .eventState
        sendMessageResultMutable.postValue(result)
    }
}

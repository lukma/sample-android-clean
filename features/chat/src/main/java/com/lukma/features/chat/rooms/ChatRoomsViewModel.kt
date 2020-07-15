package com.lukma.features.chat.rooms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lukma.core.domain.chat.ChatRoom
import com.lukma.core.domain.chat.usecase.GetChatRoomsUseCase
import com.lukma.core.domain.getOrNull
import kotlinx.coroutines.flow.collect
import org.koin.core.KoinComponent
import org.koin.core.inject

class ChatRoomsViewModel : ViewModel(), KoinComponent {
    private val getChatRoomsUseCase: GetChatRoomsUseCase by inject()

    private val roomsMutable = MutableLiveData<List<ChatRoom>>()
    internal val rooms: LiveData<List<ChatRoom>> = roomsMutable

    suspend fun getRooms() {
        getChatRoomsUseCase
            .invoke()
            .getOrNull()
            ?.collect { roomsMutable.postValue(it) }
    }
}

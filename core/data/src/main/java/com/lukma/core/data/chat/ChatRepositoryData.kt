package com.lukma.core.data.chat

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.lukma.core.data.chat.cloud.ChatMessageData
import com.lukma.core.data.chat.cloud.ChatRoomData
import com.lukma.core.data.common.NotLoggedInException
import com.lukma.core.data.common.snapshotAsFlow
import com.lukma.core.domain.chat.ChatMessage
import com.lukma.core.domain.chat.ChatRepository
import com.lukma.core.domain.chat.ChatRoom
import com.lukma.core.domain.chat.SendMessageAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class ChatRepositoryData(
    private val firebaseAuth: FirebaseAuth,
    private val roomsCollection: CollectionReference,
    private val messagesCollection: CollectionReference
) : ChatRepository {

    override suspend fun getChatRooms(): Flow<List<ChatRoom>> {
        val memberEmail = firebaseAuth.currentUser?.email ?: throw NotLoggedInException()
        return roomsCollection
            .whereArrayContains("members", memberEmail)
            .snapshotAsFlow { it.toObject(ChatRoomData::class.java)?.toEntity(withId = it.id) }
    }

    override suspend fun getChatRoomByMembers(members: List<String>): ChatRoom? {
        val sender = firebaseAuth.currentUser?.email ?: throw NotLoggedInException()
        val membersCombined = members.toMutableList().apply { add(sender) }
        return roomsCollection
            .whereArrayContainsAny("members", membersCombined.sorted())
            .get()
            .await()
            .documents
            .firstOrNull()
            ?.let { it.toObject(ChatRoomData::class.java)?.toEntity(withId = it.id) }
    }

    override suspend fun getChatMessages(roomId: String): Flow<List<ChatMessage>> =
        messagesCollection
            .whereEqualTo("roomId", roomId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .snapshotAsFlow { it.toObject(ChatMessageData::class.java)?.toEntity() }

    override suspend fun sendMessage(action: SendMessageAction) {
        val currentUser = firebaseAuth.currentUser
        val createdBy = ChatMessageData.CreatedBBy(
            email = currentUser?.email ?: throw NotLoggedInException(),
            displayName = currentUser.displayName ?: throw NotLoggedInException(),
            photoUrl = currentUser.photoUrl?.path ?: ""
        )

        val roomId = when (action) {
            is SendMessageAction.New -> roomsCollection
                .add(ChatRoomData(members = listOf(createdBy.email!!, action.to).sorted()))
                .await()
                .id
            is SendMessageAction.Reply -> action.roomId
        }
        val message = ChatMessageData(
            roomId = roomId,
            content = action.content,
            createdBy = createdBy
        )

        val scope = CoroutineScope(Dispatchers.IO)
        val addMessageTask = scope.async {
            messagesCollection
                .add(message)
                .await()
        }
        val updateRoomTask = scope.async {
            roomsCollection
                .document(roomId)
                .update(mapOf("lastMessage" to message))
                .await()
        }
        listOf(addMessageTask, updateRoomTask).awaitAll()
    }
}

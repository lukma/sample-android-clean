package com.lukma.core.data.chat

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.lukma.core.data.chat.cloud.ChatRoomData
import com.lukma.core.data.common.snapshotAsFlow
import com.lukma.core.domain.chat.ChatMessage
import com.lukma.core.domain.chat.ChatRoom
import com.lukma.core.domain.chat.SendMessageAction
import com.lukma.core.domain.user.User
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

class ChatRepositoryTest {
    private val firebaseAuth: FirebaseAuth = mockk()
    private val roomsCollection: CollectionReference = mockk()
    private val messagesCollection: CollectionReference = mockk()
    private val repository = ChatRepositoryData(
        firebaseAuth,
        roomsCollection,
        messagesCollection
    )

    @BeforeEach
    fun setup() {
        mockkStatic("kotlinx.coroutines.tasks.TasksKt")
        mockkStatic("com.lukma.core.data.common.ExtensionsKt")
        every { firebaseAuth.currentUser } returns mockk {
            every { email } returns "dummy@mail.com"
            every { displayName } returns "dummy"
        }
    }

    @Nested
    inner class GetChatRooms {

        @Test
        fun `given success process when get chat rooms then got data`() {
            // given
            val rooms = flowOf(
                listOf(
                    ChatRoom(
                        id = "someId",
                        members = listOf("dummy@mail.com", "other@mail.com"),
                        lastMessage = ChatMessage(
                            content = "lorem ipsum",
                            createdBy = User(
                                email = "dummy@mail.com",
                                displayName = "dummy",
                                photoUrl = "https://someurl"
                            ),
                            createdAt = Date()
                        )
                    )
                )
            )
            every {
                roomsCollection.whereArrayContains("members", any()).snapshotAsFlow<ChatRoom>(any())
            } returns rooms

            // when
            val result = runBlocking { repository.getChatRooms() }

            // then
            verify { roomsCollection.whereArrayContains("members", "dummy@mail.com") }
            assertEquals(rooms, result)
        }

        @Test
        fun `given fail process when get chat rooms then throw error`() {
            // given
            val error = Exception("failed")
            every { roomsCollection.whereArrayContains("members", any()) } throws error

            // when
            val result = runCatching {
                runBlocking { repository.getChatRooms() }
            }.exceptionOrNull()

            // then
            assertEquals(error, result)
        }
    }

    @Nested
    inner class GetChatRoomByMembers {

        @Test
        fun `given success process when get chat room by other member then got data`() {
            // given
            val members = listOf("dummy@mail.com", "other@mail.com")
            val room = ChatRoom(
                id = "someId",
                members = members,
                lastMessage = ChatMessage(
                    content = "lorem ipsum",
                    createdBy = User(
                        email = "dummy@mail.com",
                        displayName = "dummy",
                        photoUrl = "https://someurl"
                    ),
                    createdAt = Date()
                )
            )
            val snapshot = mockk<DocumentSnapshot> {
                every { id } returns "someId"
                every { toObject(ChatRoomData::class.java)?.toEntity(any()) } returns room
            }
            coEvery {
                roomsCollection.whereArrayContainsAny("members", any()).get().await().documents
            } returns listOf(snapshot)

            // when
            val result = runBlocking { repository.getChatRoomByMembers(listOf("other@mail.com")) }

            // then
            verify { roomsCollection.whereArrayContainsAny("members", members) }
            assertEquals(room, result)
        }

        @Test
        fun `given fail process when get chat room by other member then throw error`() {
            // given
            val error = Exception("failed")
            every { roomsCollection.whereArrayContainsAny("members", any()) } throws error

            // when
            val result = runCatching {
                runBlocking { repository.getChatRoomByMembers(listOf("other@mail.com")) }
            }.exceptionOrNull()

            // then
            assertEquals(error, result)
        }
    }

    @Nested
    inner class GetChatMessages {

        @Test
        fun `given success process when get chat messages then got data`() {
            // given
            val roomId = "someId"
            val messages = flowOf(
                listOf(
                    ChatMessage(
                        content = "lorem ipsum",
                        createdBy = User(
                            email = "dummy@mail.com",
                            displayName = "dummy",
                            photoUrl = "https://someurl"
                        ),
                        createdAt = Date()
                    )
                )
            )
            every {
                messagesCollection.whereEqualTo("roomId", any())
                    .orderBy("createdAt", any())
                    .snapshotAsFlow<ChatMessage>(any())
            } returns messages

            // when
            val result = runBlocking { repository.getChatMessages(roomId) }

            // then
            verify { messagesCollection.whereEqualTo("roomId", roomId) }
            assertEquals(messages, result)
        }

        @Test
        fun `given fail process when get chat messages then throw error`() {
            // given
            val error = Exception("failed")
            every { messagesCollection.whereEqualTo("roomId", any()) } throws error

            // when
            val result = runCatching {
                runBlocking { repository.getChatMessages("someId") }
            }.exceptionOrNull()

            // then
            assertEquals(error, result)
        }
    }

    @Nested
    inner class SendMessage {

        @Test
        fun `given success process when send new chat messages then do nothing`() {
            // given
            val action = SendMessageAction.New(
                to = "dummy@mail.com",
                content = "lorem ipsum"
            )
            val roomId = "someId"
            coEvery { messagesCollection.add(any()).await() } returns mockk()
            coEvery { roomsCollection.add(any()).await().id } returns roomId
            coJustRun { roomsCollection.document(roomId).update(any()).await() }

            // when
            val result = runBlocking { repository.sendMessage(action) }

            // then
            verifyAll {
                messagesCollection.add(any())
                roomsCollection.add(any())
                roomsCollection.document(roomId).update(any())
            }
            assertEquals(Unit, result)
        }

        @Test
        fun `given success process when send reply chat messages then do nothing`() {
            // given
            val action = SendMessageAction.Reply(
                roomId = "someId",
                content = "lorem ipsum"
            )
            coEvery { messagesCollection.add(any()).await() } returns mockk()
            coJustRun { roomsCollection.document(action.roomId).update(any()).await() }

            // when
            val result = runBlocking { repository.sendMessage(action) }

            // then
            verifyAll {
                messagesCollection.add(any())
                roomsCollection.document(action.roomId).update(any())
            }
            assertEquals(Unit, result)
        }

        @Test
        fun `given fail process when send messages then throw error`() {
            // given
            val action = SendMessageAction.New(
                to = "dummy@mail.com",
                content = "lorem ipsum"
            )
            val error = Exception("failed")
            every { roomsCollection.add(any()) } throws error

            // when
            val result = runCatching {
                runBlocking { repository.sendMessage(action) }
            }.exceptionOrNull()

            // then
            assertEquals(error, result)
        }
    }
}

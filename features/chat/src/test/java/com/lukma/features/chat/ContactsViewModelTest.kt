package com.lukma.features.chat

import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lukma.core.domain.Either
import com.lukma.core.domain.user.User
import com.lukma.core.domain.user.usecase.SearchUsersUseCase
import com.lukma.core.uikit.paging.AppPagingSource
import com.lukma.features.chat.contacts.ContactsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

@ExtendWith(TaskExecutorExtension::class)
class ContactsViewModelTest : KoinTest {
    private val searchUsersUseCase: SearchUsersUseCase = mockk()
    private lateinit var viewModel: ContactsViewModel

    @BeforeEach
    fun setup() {
        stopKoin()
        startKoin {
            modules(module {
                single { searchUsersUseCase }
            })
        }
        viewModel = ContactsViewModel()
    }

    @Nested
    inner class SearchUsers {

        @Test
        fun `given success process when search users then collect data`() {
            // given
            val users = listOf(
                User(
                    email = "dummy@mail.com",
                    displayName = "dummy"
                )
            )
            coEvery { searchUsersUseCase.addParams(any()).invoke() } returns Either.Value(users)

            runBlocking {
                // when
                viewModel.searchUsers("")

                // then
                val expected = Pager(PagingConfig(pageSize = 20)) {
                    AppPagingSource { users }
                }.flow.asLiveData().value
                assertEquals(expected, viewModel.users.asLiveData().value)
            }
        }

        @Test
        fun `given fail process when search users then throw error`() {
            // given
            val error = Exception("failed")
            coEvery { searchUsersUseCase.addParams(any()).invoke() } returns Either.Error(error)

            runBlocking {
                // when
                viewModel.searchUsers("")

                // then
                viewModel.users.catch { assertEquals(error, it) }
            }
        }
    }
}

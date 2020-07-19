package com.lukma.core.data.user

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.lukma.core.data.AppDatabase
import com.lukma.core.data.user.local.UserDao
import com.lukma.core.data.user.local.UserTable
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class UserDaoTest {
    private lateinit var testDatabase: AppDatabase
    private lateinit var userDao: UserDao

    @BeforeEach
    fun setup() {
        testDatabase = Room
            .inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().context,
                AppDatabase::class.java
            )
            .build()
        userDao = testDatabase.userDao()
    }

    @AfterEach
    fun tearDown() {
        testDatabase.close()
    }

    @DisplayName("given exist users when search users then got data")
    @Test
    fun testFinds() {
        // given
        val users = listOf(
            UserTable(
                email = "dummy@mail.com",
                displayName = "dummy"
            )
        )
        runBlocking {
            userDao.insert(users)

            // when
            val result = userDao.finds("dummy", 10, 0)

            // then
            assertEquals(users, result)
        }
    }

    @DisplayName("given exist users when replace all and search users then new data")
    @Test
    fun testReplaceAll() {
        // given
        val oldUsers = listOf(
            UserTable(
                email = "dummy@mail.com",
                displayName = "dummy1"
            )
        )
        val newUsers = listOf(
            UserTable(
                email = "dummy@mail.com",
                displayName = "dummy2"
            )
        )
        runBlocking {
            userDao.insert(oldUsers)

            // when
            userDao.replaceAll(newUsers)
            val result = userDao.finds("dummy", 10, 0)

            // then
            assertEquals(newUsers, result)
        }
    }
}

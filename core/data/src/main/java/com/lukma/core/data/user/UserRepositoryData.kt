package com.lukma.core.data.user

import com.lukma.core.data.common.getDataOrThrow
import com.lukma.core.data.user.cloud.UserService
import com.lukma.core.data.user.local.UserDao
import com.lukma.core.domain.ListConfig
import com.lukma.core.domain.user.User
import com.lukma.core.domain.user.UserRepository

class UserRepositoryData(
    private val userDao: UserDao,
    private val userService: UserService
) : UserRepository {

    override suspend fun searchUsers(query: String, config: ListConfig): List<User> =
        runCatching {
            userService.searchUsers(query, config.pageSize, config.offset)
                .getDataOrThrow()
                .map(::transform)
                .also { userDao.replaceAll(it.map(::transform)) }
        }.getOrElse {
            val data = userDao.finds(query, config.pageSize, config.offset).map(::transform)
            if (data.isNotEmpty()) data else throw it
        }
}

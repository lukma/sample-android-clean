package com.lukma.core.data.user

import com.lukma.core.data.common.getDataOrThrow
import com.lukma.core.data.user.cloud.UserService
import com.lukma.core.domain.ListConfig
import com.lukma.core.domain.user.User
import com.lukma.core.domain.user.UserRepository

class UserRepositoryData(private val userService: UserService) : UserRepository {
    override suspend fun searchUsers(query: String, config: ListConfig): List<User> =
        userService.searchUsers(query, config.pageSize, config.offset)
            .getDataOrThrow()
            .map(::transform)
}

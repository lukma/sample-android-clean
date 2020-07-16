package com.lukma.core.domain.user

import com.lukma.core.domain.ListConfig

interface UserRepository {
    suspend fun searchUsers(query: String, config: ListConfig): List<User>
}

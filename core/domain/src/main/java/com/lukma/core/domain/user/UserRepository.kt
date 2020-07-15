package com.lukma.core.domain.user

interface UserRepository {
    suspend fun searchUsers(query: String): List<User>
}

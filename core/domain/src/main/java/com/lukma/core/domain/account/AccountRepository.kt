package com.lukma.core.domain.account

interface AccountRepository {
    suspend fun getProfile(): Profile
}

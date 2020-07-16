package com.lukma.core.domain.user.usecase

import com.lukma.core.domain.BaseUseCase
import com.lukma.core.domain.ListConfig
import com.lukma.core.domain.user.User
import com.lukma.core.domain.user.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class SearchUsersUseCase(private val userRepository: UserRepository) : BaseUseCase<List<User>>() {
    override val coroutineContext: CoroutineContext = Dispatchers.IO

    fun addParams(query: String, config: ListConfig = ListConfig()) = apply {
        val params = mapOf(
            KEY_QUERY to query,
            KEY_CONFIG to config
        )
        addParams(params)
    }

    override suspend fun build() = userRepository.searchUsers(
        params[KEY_QUERY] as String,
        params[KEY_CONFIG] as ListConfig
    )

    companion object {
        private const val KEY_QUERY = "KEY_QUERY"
        private const val KEY_CONFIG = "KEY_CONFIG"
    }
}

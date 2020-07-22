package com.lukma.features.chat.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.lukma.core.domain.ListConfig
import com.lukma.core.domain.getOrNull
import com.lukma.core.domain.user.usecase.SearchUsersUseCase
import com.lukma.core.uikit.paging.AppPagingSource
import org.koin.core.KoinComponent
import org.koin.core.inject

class ContactsViewModel : ViewModel(), KoinComponent {
    private val searchUsersUseCase: SearchUsersUseCase by inject()

    private var query = ""
    private val userSource = AppPagingSource {
        val config = ListConfig(
            limit = it.loadSize,
            offset = it.key ?: 0
        )
        searchUsersUseCase
            .addParams(query, config)
            .invoke()
            .getOrNull() ?: emptyList()
    }
    val users = Pager(PagingConfig(pageSize = 20)) { userSource }
        .flow
        .cachedIn(viewModelScope)

    suspend fun searchUsers(query: String) {
        this.query = query
        userSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )
    }
}

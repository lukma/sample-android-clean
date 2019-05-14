package com.lukma.clean.ui.common

import androidx.paging.PagedList

data class PagedResource<Entity>(
    val state: PagedState,
    val data: PagedList<Entity>? = null,
    val error: Throwable? = null
)

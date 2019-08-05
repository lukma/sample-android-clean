package com.lukma.android.presentation.common

data class PagedResource(
    val state: PagedState,
    val error: Throwable? = null
)

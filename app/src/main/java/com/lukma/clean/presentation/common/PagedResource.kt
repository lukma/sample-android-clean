package com.lukma.clean.presentation.common

data class PagedResource(
    val state: PagedState,
    val error: Throwable? = null
)

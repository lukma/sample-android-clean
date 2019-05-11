package com.lukma.clean.ui.common

data class Resource<Entity>(
    val state: State,
    val data: Entity? = null,
    val error: Throwable? = null
)

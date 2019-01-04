package com.lukma.clean.ui.common

data class Resource<Entity>(
    val state: ResourceState,
    val data: Entity? = null,
    val error: Throwable? = null
)

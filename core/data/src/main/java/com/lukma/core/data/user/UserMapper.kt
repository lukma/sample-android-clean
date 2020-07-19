package com.lukma.core.data.user

import com.lukma.core.data.user.cloud.response.UserResponse
import com.lukma.core.data.user.local.UserTable
import com.lukma.core.domain.user.User

fun transform(value: UserResponse) = User(
    email = value.email ?: throw NoSuchElementException(),
    displayName = value.displayName ?: throw NoSuchElementException(),
    photoUrl = value.photoUrl ?: throw NoSuchElementException()
)

fun transform(value: UserTable) = User(
    email = value.email,
    displayName = value.displayName ?: throw NoSuchElementException(),
    photoUrl = value.photoUrl ?: throw NoSuchElementException()
)

fun transform(value: User) = UserTable(
    email = value.email,
    displayName = value.displayName,
    photoUrl = value.photoUrl
)

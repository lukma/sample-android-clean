package com.lukma.core.data.user

import com.lukma.core.data.user.cloud.response.UserResponse
import com.lukma.core.domain.user.User

fun transform(value: UserResponse): User {
    val email = value.email ?: throw NoSuchElementException()
    val displayName = value.displayName ?: throw NoSuchElementException()
    return User(email, displayName)
}

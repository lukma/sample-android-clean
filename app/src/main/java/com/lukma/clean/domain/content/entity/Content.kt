package com.lukma.clean.domain.content.entity

import java.util.*

data class Content(
    val id: String,
    val title: String,
    val thumbnail: String,
    val content: String,
    val createdDate: Date
)

package com.lukma.android.data.content

import com.lukma.android.data.content.cloud.response.ContentResponse
import com.lukma.android.data.content.local.ContentTable
import com.lukma.android.domain.content.entity.Content
import com.lukma.android.shared.utils.DateUtils

fun transform(value: ContentResponse) = Content(
    value.id ?: throw NoSuchElementException("id"),
    value.title ?: throw NoSuchElementException("title"),
    value.thumbnail ?: throw NoSuchElementException("thumbnail"),
    value.content ?: throw NoSuchElementException("content"),
    DateUtils.toDate(
        value.createdDate ?: throw NoSuchElementException("createdDate"),
        DateUtils.TIMESTAMP_FORMAT
    )
)

fun transform(value: ContentTable) = Content(
    value.id,
    value.title,
    value.thumbnail,
    value.content,
    value.createdDate
)

fun transform(value: Content) = ContentTable(
    value.id,
    value.title,
    value.thumbnail,
    value.content,
    value.createdDate
)

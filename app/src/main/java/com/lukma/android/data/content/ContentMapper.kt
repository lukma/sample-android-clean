package com.lukma.android.data.content

import com.lukma.android.data.content.cloud.response.ContentResponse
import com.lukma.android.data.content.local.ContentTable
import com.lukma.android.domain.content.entity.Content
import com.lukma.android.shared.utils.DateUtils

fun transform(values: ContentResponse) = values.let {
    Content(
        it.id ?: throw NoSuchElementException("id"),
        it.title ?: throw NoSuchElementException("title"),
        it.thumbnail ?: throw NoSuchElementException("thumbnail"),
        it.content ?: throw NoSuchElementException("content"),
        DateUtils.toDate(
            it.createdDate ?: throw NoSuchElementException("createdDate"),
            DateUtils.TIMESTAMP_FORMAT
        )
    )
}

fun transform(values: ContentTable) = values.let {
    Content(
        it.id,
        it.title,
        it.thumbnail,
        it.content,
        it.createdDate
    )
}

fun transform(values: Content) = values.let {
    ContentTable(
        it.id,
        it.title,
        it.thumbnail,
        it.content,
        it.createdDate
    )
}
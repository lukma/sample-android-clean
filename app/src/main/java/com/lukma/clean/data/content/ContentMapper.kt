package com.lukma.clean.data.content

import com.lukma.clean.data.content.cloud.response.GetContentsResponse
import com.lukma.clean.data.content.local.ContentTable
import com.lukma.clean.domain.common.utils.DateUtils
import com.lukma.clean.domain.content.entity.Content
import org.koin.core.error.MissingPropertyException

fun transform(values: GetContentsResponse.DataResponse) = values.let {
    Content(
        it.id ?: throw MissingPropertyException("id"),
        it.title ?: throw MissingPropertyException("title"),
        it.thumbnail ?: throw MissingPropertyException("thumbnail"),
        it.content ?: throw MissingPropertyException("content"),
        DateUtils.toDate(
            it.createdDate ?: throw MissingPropertyException("createdDate"),
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

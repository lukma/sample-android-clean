package com.lukma.clean.data.content.store

import com.lukma.clean.data.content.store.cloud.response.GetContentsResponse
import com.lukma.clean.data.content.store.database.ContentTable
import com.lukma.clean.domain.content.Content

class ContentMapper {
    fun transform(value: Content) = ContentTable(
        value.id,
        value.title,
        value.thumbnail,
        value.content
    )

    fun transform(value: GetContentsResponse.DataResponse) = Content(
        value.id,
        value.title,
        value.thumbnail,
        value.content
    )

    fun transform(values: GetContentsResponse) = values.data.map { transform(it) }

    fun transform(value: ContentTable) = Content(
        value.id,
        value.title,
        value.thumbnail,
        value.content
    )

    fun transform(values: List<ContentTable>) = values.map { transform(it) }
}
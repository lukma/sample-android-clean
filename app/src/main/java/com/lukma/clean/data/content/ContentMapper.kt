package com.lukma.clean.data.content

import com.lukma.clean.data.content.entity.response.GetContentsResponse
import com.lukma.clean.data.content.entity.table.ContentTable
import com.lukma.clean.domain.content.entity.Content

object ContentMapper {

    fun transform(values: GetContentsResponse) = values.data.map {
        Content(
            it.id,
            it.title,
            it.thumbnail,
            it.content
        )
    }

    fun transform(values: List<Content>) = values.map {
        ContentTable(
            it.id,
            it.title,
            it.thumbnail,
            it.content
        )
    }
}

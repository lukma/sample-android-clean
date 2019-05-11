package com.lukma.clean.data.content

import com.lukma.clean.data.content.cloud.response.GetContentsResponse
import com.lukma.clean.data.content.local.ContentTable
import com.lukma.clean.domain.content.entity.Content

object ContentMapper {
    fun transformToEntity(values: GetContentsResponse) = values.data.map {
        Content(
            it.id,
            it.title,
            it.thumbnail,
            it.content
        )
    }

    fun transformToEntity(values: List<ContentTable>) = values.map {
        Content(
            it.id,
            it.title,
            it.thumbnail,
            it.content
        )
    }

    fun transformToTable(values: List<Content>) = values.map {
        ContentTable(
            it.id,
            it.title,
            it.thumbnail,
            it.content
        )
    }
}

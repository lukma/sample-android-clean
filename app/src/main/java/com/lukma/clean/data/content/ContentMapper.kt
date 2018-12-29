package com.lukma.clean.data.content

import com.lukma.clean.data.content.entity.GetContentsResponse
import com.lukma.clean.domain.content.Content

object ContentMapper {
    fun transform(value: GetContentsResponse.DataResponse) = Content(
        value.id,
        value.title,
        value.thumbnail,
        value.content
    )

    fun transform(values: GetContentsResponse) = values.data.map { transform(it) }
}
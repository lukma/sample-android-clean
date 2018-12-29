package com.lukma.clean.domain.content.interactor

import com.lukma.clean.domain.common.UseCase
import com.lukma.clean.domain.common.UseCaseConstant
import com.lukma.clean.domain.content.Content
import com.lukma.clean.domain.content.ContentRepository

class GetContents(private val repository: ContentRepository) : UseCase<List<Content>>() {
    override fun build(params: Map<String, Any?>) = repository.gets(
        params[UseCaseConstant.LIMIT] as Int,
        params[UseCaseConstant.OFFSET] as Int
    )
}
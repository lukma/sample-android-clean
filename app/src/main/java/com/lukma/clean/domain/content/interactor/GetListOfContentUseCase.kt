package com.lukma.clean.domain.content.interactor

import com.lukma.clean.domain.common.UseCase
import com.lukma.clean.domain.common.UseCaseConstant
import com.lukma.clean.domain.content.entity.Content
import com.lukma.clean.domain.content.ContentRepository

class GetListOfContentUseCase(private val repository: ContentRepository) : UseCase<List<Content>>() {
    override fun build(params: Map<String, Any?>) = repository.gets(
        params[UseCaseConstant.LIMIT] as Int,
        params[UseCaseConstant.OFFSET] as Int
    )
}

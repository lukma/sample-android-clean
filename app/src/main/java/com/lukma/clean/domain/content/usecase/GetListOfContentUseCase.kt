package com.lukma.clean.domain.content.usecase

import com.lukma.clean.domain.common.UseCaseConstant
import com.lukma.clean.domain.common.base.BaseDeferredUseCase
import com.lukma.clean.domain.content.ContentRepository
import com.lukma.clean.domain.content.entity.Content

class GetListOfContentUseCase(private val repository: ContentRepository) :
    BaseDeferredUseCase<List<Content>>() {

    override suspend fun build(params: Map<String, Any?>) = repository.gets(
        params[UseCaseConstant.LIMIT] as Int,
        params[UseCaseConstant.OFFSET] as Int
    )
}

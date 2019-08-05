package com.lukma.android.domain.content.usecase

import com.lukma.android.domain.common.UseCaseConstant
import com.lukma.android.domain.common.base.BaseUseCase
import com.lukma.android.domain.content.ContentRepository
import com.lukma.android.domain.content.entity.Content

class GetListOfContentUseCase(private val repository: ContentRepository) :
    BaseUseCase<List<Content>>() {

    override suspend fun build(params: Map<String, Any?>) = repository.gets(
        params[UseCaseConstant.LIMIT] as Int,
        params[UseCaseConstant.OFFSET] as Int
    )
}

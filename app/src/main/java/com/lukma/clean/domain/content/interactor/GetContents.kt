package com.lukma.clean.domain.content.interactor

import com.lukma.clean.data.common.ListParams
import com.lukma.clean.domain.content.Content
import com.lukma.clean.domain.common.UseCase
import com.lukma.clean.domain.content.ContentRepository

class GetContents(private val repository: ContentRepository) : UseCase<List<Content>, ListParams>() {
    override fun build(params: ListParams) = repository.gets(params)
}
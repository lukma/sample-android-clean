package com.lukma.clean.data.content.interactor

import com.lukma.clean.data.common.ListParams
import com.lukma.clean.data.common.UseCase
import com.lukma.clean.data.content.Content
import com.lukma.clean.data.content.ContentRepository

class GetContents(private val repository: ContentRepository) : UseCase<List<Content>, ListParams>() {
    override fun build(params: ListParams) = repository.gets(params)
}
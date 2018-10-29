package com.lukma.clean.domain.content

import com.lukma.clean.data.common.ListParams
import io.reactivex.Flowable

interface ContentRepository {
    fun gets(params: ListParams): Flowable<List<Content>>

    fun insert(data: List<Content>): Flowable<Boolean>
}
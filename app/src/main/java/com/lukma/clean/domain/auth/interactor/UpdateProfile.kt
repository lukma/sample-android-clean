package com.lukma.clean.domain.auth.interactor

import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.domain.common.UseCase

class UpdateProfile(private val repository: AuthRepository)
    : UseCase<Void, UpdateProfile.Params?>() {
    override fun build(params: Params?) = repository.updateProfile(params?.fullName.orEmpty())

    data class Params(val fullName: String)
}
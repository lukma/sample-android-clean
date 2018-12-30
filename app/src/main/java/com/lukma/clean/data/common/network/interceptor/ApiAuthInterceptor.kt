package com.lukma.clean.data.common.network.interceptor

import com.lukma.clean.BuildConfig
import com.lukma.clean.data.common.network.exception.ApiException
import com.lukma.clean.data.common.network.exception.TokenUnauthorizedException
import com.lukma.clean.domain.auth.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.io.IOException

class ApiAuthInterceptor(private val type: Type) : Interceptor, KoinComponent {
    private val authRepository by inject<AuthRepository>()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val authorization = if (type == Type.BASIC_AUTH) BuildConfig.BASIC_AUTH_VALUE
        else runBlocking {
            val authIsActive = authRepository.getAuthIsActive().await()
            "${authIsActive.tokenType} ${authIsActive.accessToken}"
        }

        val original = chain.request()
        val response = requestApi(chain, original, authorization)
        if (response.code() == 401 && type == Type.BEARER) {
            return runBlocking {
                val newToken = authRepository.refreshToken().await()
                requestApi(chain, original, newToken.accessToken).also {
                    if (response.code() == 401) {
                        throw TokenUnauthorizedException()
                    }
                }
            }
        } else if (response.code() == 401 && type == Type.BASIC_AUTH) {
            throw  TokenUnauthorizedException()
        } else if (response.code() < 200 || response.code() >= 400) {
            throw ApiException(JSONObject(response.body()?.string()))
        }

        return response
    }

    private fun requestApi(
        chain: Interceptor.Chain,
        original: Request,
        authorization: String
    ): Response {
        val request = original.newBuilder()
            .header("Authorization", authorization)
            .method(original.method(), original.body())
            .build()
        return chain.proceed(request)
    }

    enum class Type { BASIC_AUTH, BEARER }
}
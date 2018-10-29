package com.lukma.clean.data.network.interceptor

import com.lukma.clean.BuildConfig
import com.lukma.clean.domain.auth.Auth
import com.lukma.clean.domain.auth.AuthRepository
import com.lukma.clean.data.exception.ApiException
import com.lukma.clean.data.network.AuthType
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.io.IOException

class ApiAuthInterceptor(private val type: AuthType) : Interceptor, KoinComponent {
    private val authRepository by inject<AuthRepository>()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var auth: Auth? = null
        if (type == AuthType.BEARER) {
            auth = authRepository.getIsActive().blockingFirst()
        }

        val authorization = if (type == AuthType.BASIC_AUTH) {
            BuildConfig.BASIC_AUTH_VALUE
        } else {
            "${auth?.tokenType} ${auth?.accessToken}"
        }

        val original = chain.request()
        val response = requestApi(chain, original, authorization)
        if (response.code() == 401 && type == AuthType.BEARER) {
            return authRepository
                    .refreshToken(auth?.refreshToken.orEmpty())
                    .doOnNext { authRepository.update(it) }
                    .map { requestApi(chain, original, it.accessToken) }
                    .blockingFirst()
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
}
package com.lukma.android.data.common.interceptor

import com.lukma.android.data.common.entity.RetrofitType
import com.lukma.android.data.common.exception.network.ApiException
import com.lukma.android.data.common.exception.network.TokenUnauthorizedException
import com.lukma.android.domain.auth.AuthRepository
import com.lukma.android.domain.auth.entity.Auth
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.IOException

class ApiInterceptor(private val type: RetrofitType) : Interceptor, KoinComponent {
    private val authRepository by inject<AuthRepository>()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val response = if (type == RetrofitType.TOKEN) {
            runBlocking {
                val session = authRepository.getAuthIsActive()
                requestWithAuthorization(chain, original, session)
            }
        } else {
            chain.proceed(original)
        }

        when {
            response.code() == 401 && type == RetrofitType.TOKEN -> {
                return runBlocking {
                    authRepository.refreshToken()
                    val session = authRepository.getAuthIsActive()
                    requestWithAuthorization(chain, original, session).also {
                        if (response.code() == 401) throw TokenUnauthorizedException()
                    }
                }
            }
            response.code() == 401 && type == RetrofitType.BASIC_AUTH ->
                throw  TokenUnauthorizedException()
            response.code() < 200 || response.code() >= 400 ->
                throw ApiException(JSONObject(response.body()?.string()))
        }

        return response
    }

    private fun requestWithAuthorization(
        chain: Interceptor.Chain,
        original: Request,
        session: Auth
    ) = original.newBuilder()
        .header("Authorization", "${session.tokenType} ${session.accessToken}")
        .method(original.method(), original.body())
        .build()
        .let(chain::proceed)
}

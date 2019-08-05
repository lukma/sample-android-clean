package com.lukma.android.data.common.interceptor

import com.lukma.android.data.common.entity.RetrofitType
import com.lukma.android.data.common.exception.network.ApiException
import com.lukma.android.data.common.exception.network.TokenUnauthorizedException
import com.lukma.android.domain.auth.entity.Auth
import com.lukma.android.domain.auth.usecase.GetAuthIsActiveUseCase
import com.lukma.android.domain.auth.usecase.RefreshTokenUseCase
import com.lukma.android.domain.common.entity.Either
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.IOException

class ApiInterceptor(private val type: RetrofitType) : Interceptor, KoinComponent {
    private val getAuthIsActiveUseCase by inject<GetAuthIsActiveUseCase>()
    private val refreshTokenUseCase by inject<RefreshTokenUseCase>()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = if (type == RetrofitType.TOKEN) {
            val session = when (val result = runBlocking { getAuthIsActiveUseCase.invoke() }) {
                is Either.Value -> result.value
                is Either.Error -> getNewSession()
            }
            requestWithAuthorization(chain, session)
        } else {
            val original = chain.request()
            chain.proceed(original)
        }

        return checkIfHasError(response) {
            val session = getNewSession()
            requestWithAuthorization(chain, session)
        }
    }

    private fun getNewSession() = runBlocking {
        when (val result = refreshTokenUseCase.invoke()) {
            is Either.Value -> result.value
            is Either.Error -> throw IOException("Unexpected Error")
        }
    }

    private fun requestWithAuthorization(chain: Interceptor.Chain, session: Auth): Response {
        val original = chain.request()
        val request = original.newBuilder().apply {
            header("Authorization", "${session.tokenType} ${session.accessToken}")
            method(original.method(), original.body())
        }.build()
        return chain.proceed(request)
    }

    private fun checkIfHasError(
        response: Response,
        retryCount: Int = 0,
        onExpire: suspend (retryCount: Int) -> Response
    ): Response {
        when {
            response.code() == 401 && type == RetrofitType.TOKEN -> {
                if (retryCount < 3) {
                    return runBlocking { onExpire(retryCount + 1) }
                } else {
                    throw IOException("Unexpected Error")
                }
            }
            response.code() == 401 && type == RetrofitType.BASIC_AUTH ->
                throw  TokenUnauthorizedException()
            response.code() < 200 || response.code() >= 400 ->
                throw ApiException(JSONObject(response.body()?.string()))
        }

        return response
    }
}

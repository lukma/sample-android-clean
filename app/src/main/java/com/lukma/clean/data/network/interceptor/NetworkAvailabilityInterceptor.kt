package com.lukma.clean.data.network.interceptor

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import com.lukma.clean.data.exception.NoNetworkException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkAvailabilityInterceptor(private val context: Context) : Interceptor {

    @SuppressLint("MissingPermission")
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!isNetworkAvailable()) {
            throw NoNetworkException()
        }
        return chain.proceed(request)
    }
}
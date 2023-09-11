package com.charlyco.itrash.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val authToken: String?): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
            val request = if (authToken != null) {
                Log.i("Token","Adding Authorization header with token: $authToken")
                originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer $authToken")
                    .build()
        } else {
            Log.i("Token", "No token provided, proceeding with the original request.")
            originalRequest
            }
        return chain.proceed(request)
    }
}
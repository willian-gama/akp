package com.android.dev.engineer.kotlin.compose.data.fake.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ChuckerInterceptorFake : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}
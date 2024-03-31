package com.android.dev.engineer.kotlin.compose.data.extension

import com.android.dev.engineer.kotlin.compose.data.api.TheMovieApi
import com.android.dev.engineer.kotlin.compose.data.di.NetworkModule.provideCallAdapterFactory
import com.android.dev.engineer.kotlin.compose.data.di.NetworkModule.provideMoshi
import com.android.dev.engineer.kotlin.compose.data.di.NetworkModule.provideMoshiConverterFactory
import com.android.dev.engineer.kotlin.compose.data.di.NetworkModule.provideOkHttpClient
import com.android.dev.engineer.kotlin.compose.data.di.NetworkModule.provideRetrofitBuilder
import com.android.dev.engineer.kotlin.compose.data.di.NetworkModule.provideTheMovieApi
import com.android.dev.engineer.kotlin.compose.data.fake.interceptor.ChuckerInterceptorFake
import com.android.dev.engineer.kotlin.compose.data.interceptor.ApiKeyInterceptor
import com.android.dev.engineer.kotlin.compose.data.use_case.api_error_handling.ApiErrorHandlingUseCase
import okhttp3.mockwebserver.MockWebServer

fun MockWebServer.toTheMovieApi(
    apiErrorHandlingUseCase: ApiErrorHandlingUseCase
): TheMovieApi {
    val retrofitBuilder = provideRetrofitBuilder(
        baseUrl = url("").toString(),
        okHttpClient = provideOkHttpClient(
            apiKeyInterceptor = ApiKeyInterceptor(),
            chuckerInterceptor = ChuckerInterceptorFake()
        ),
        moshiConverterFactory = provideMoshiConverterFactory(moshi = provideMoshi()),
        callAdapterFactory = provideCallAdapterFactory(apiErrorHandlingUseCase = apiErrorHandlingUseCase)
    )
    return provideTheMovieApi(retrofitBuilder = retrofitBuilder)
}
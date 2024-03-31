package com.android.dev.engineer.kotlin.compose.data.di

import android.content.Context
import com.android.dev.engineer.kotlin.compose.data.adapter.CallAdapterFactory
import com.android.dev.engineer.kotlin.compose.data.api.TheMovieApi
import com.android.dev.engineer.kotlin.compose.data.api.TheMovieApi.Companion.BASE_API_URL
import com.android.dev.engineer.kotlin.compose.data.interceptor.ApiKeyInterceptor
import com.android.dev.engineer.kotlin.compose.data.use_case.api_error_handling.ApiErrorHandlingUseCase
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val READ_TIMEOUT_SECS = 60L
    private const val CONNECT_TIMEOUT_SECS = 60L

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @ApiKey
    @Singleton
    @Provides
    fun provideApiKeyInterceptor(apiKeyInterceptor: ApiKeyInterceptor): Interceptor {
        return apiKeyInterceptor
    }

    @ChuckerKey
    @Singleton
    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext appContext: Context
    ): Interceptor {
        val chuckerCollector = ChuckerCollector(
            context = appContext,
            showNotification = true
        )
        return ChuckerInterceptor
            .Builder(context = appContext)
            .collector(chuckerCollector)
            .createShortcut(enable = true) // Tap and hold to access app options from home screen
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApiKey apiKeyInterceptor: Interceptor,
        @ChuckerKey chuckerInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .connectTimeout(CONNECT_TIMEOUT_SECS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECS, TimeUnit.SECONDS)
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(chuckerInterceptor)
            .build()
    }

    @TheMovieApiBaseUrl
    @Singleton
    @Provides
    fun provideBaseApiUrl(): String {
        return BASE_API_URL
    }

    @Singleton
    @Provides
    fun provideCallAdapterFactory(
        apiErrorHandlingUseCase: ApiErrorHandlingUseCase
    ): CallAdapterFactory {
        return CallAdapterFactory(
            apiErrorHandlingUseCase = apiErrorHandlingUseCase
        )
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        @TheMovieApiBaseUrl baseUrl: String,
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient,
        callAdapterFactory: CallAdapterFactory
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(moshiConverterFactory)
            .client(okHttpClient)
    }

    @Singleton
    @Provides
    fun provideTheMovieApi(retrofitBuilder: Retrofit.Builder): TheMovieApi {
        return retrofitBuilder.build().create(TheMovieApi::class.java)
    }
}
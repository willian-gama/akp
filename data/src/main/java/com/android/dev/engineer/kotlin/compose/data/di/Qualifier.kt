package com.android.dev.engineer.kotlin.compose.data.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppPrefs

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DataStoreCoroutineScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TheMovieApiBaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiKey

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ChuckerKey

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EncryptedSharedPrefsKey
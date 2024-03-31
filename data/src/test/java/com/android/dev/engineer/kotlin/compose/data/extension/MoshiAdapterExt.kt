package com.android.dev.engineer.kotlin.compose.data.extension

import com.android.dev.engineer.kotlin.compose.data.di.NetworkModule.provideMoshi

object MoshiAdapterExt {
    private val moshi by lazy { provideMoshi() }

    internal inline fun <reified T> T.toJson(): String {
        return moshi.adapter(T::class.java).toJson(this)
    }

    internal inline fun <reified T> String.fromJson(): T {
        return moshi.adapter(T::class.java).fromJson(this)!!
    }
}
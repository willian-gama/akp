package com.android.dev.engineer.kotlin.compose.data.extension

import com.squareup.moshi.Moshi

inline fun <reified T> Moshi.fromJson(json: String): T? {
    return adapter(T::class.java).fromJson(json)
}
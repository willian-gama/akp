package com.android.dev.engineer.kotlin.compose.app

import android.app.Application
import com.android.dev.engineer.kotlin.compose.data.BuildConfig
import com.android.dev.engineer.kotlin.compose.data.timber.TimberDebugTree
import com.android.dev.engineer.kotlin.compose.ui.util.ExcludeFromJacocoGeneratedReport
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@ExcludeFromJacocoGeneratedReport
@HiltAndroidApp
class KotlinComposeApp : KotlinComposeAppImpl()

open class KotlinComposeAppImpl : Application() {
    override fun onCreate() {
        super.onCreate()
        setUpTimber()
    }

    private fun setUpTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(TimberDebugTree())
        }
    }
}
package com.android.dev.engineer.kotlin.compose.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.os.bundleOf
import dagger.hilt.android.AndroidEntryPoint

// Hilt gradle plugin causes Jacoco coverage issue: https://github.com/google/dagger/issues/1982
@AndroidEntryPoint(ComponentActivity::class)
class MainActivity : Hilt_MainActivity() {
    companion object {
        private const val START_DESTINATION_KEY = "start_destination_key"

        fun prepareBundle(startDestination: String): Bundle {
            return bundleOf(START_DESTINATION_KEY to startDestination)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainNavHostComposable(
                startDestination = requireNotNull(intent.extras?.getString(START_DESTINATION_KEY))
            )
        }
    }
}
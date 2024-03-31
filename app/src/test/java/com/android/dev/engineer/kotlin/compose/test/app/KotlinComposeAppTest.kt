package com.android.dev.engineer.kotlin.compose.test.app

import com.android.dev.engineer.kotlin.compose.app.KotlinComposeAppImpl
import com.android.dev.engineer.kotlin.compose.data.timber.TimberDebugTree
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import timber.log.Timber

@RunWith(RobolectricTestRunner::class)
class KotlinComposeAppTest {
    private lateinit var kotlinComposeApp: KotlinComposeAppImpl

    @Before
    fun setUp() {
        kotlinComposeApp = KotlinComposeAppImpl()
    }

    @Test
    fun testKotlinCompose() {
        kotlinComposeApp.onCreate()
        assertEquals(1, Timber.treeCount)
        assertTrue(Timber.forest().first() is TimberDebugTree)
    }
}
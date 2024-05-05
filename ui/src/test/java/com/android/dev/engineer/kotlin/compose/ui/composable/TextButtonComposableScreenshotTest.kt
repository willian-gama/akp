package com.android.dev.engineer.kotlin.compose.ui.composable

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.dev.engineer.kotlin.compose.ui.theme.KotlinComposeAppTheme
import com.android.ide.common.rendering.api.SessionParams
import org.junit.Rule
import org.junit.Test

class TextButtonComposableScreenshotTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig(softButtons = false),
        renderingMode = SessionParams.RenderingMode.SHRINK,
        showSystemUi = true
    )

    @Test
    fun testScreenshotTextButtonComposable() {
        paparazzi.snapshot {
            KotlinComposeAppTheme {
                TextButtonComposable(
                    text = "Skip",
                    onClick = {}
                )
            }
        }
    }
}
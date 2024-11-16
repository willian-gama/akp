@file:OptIn(ExperimentalCoroutinesApi::class)

package com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.test.screen

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import coil3.Coil
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.test.FakeImageLoaderEngine
import com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.UpcomingMovieComposable
import com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.coroutines.MainTestRule
import com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.fake.domain.MovieFake.createMovieItem
import com.android.dev.engineer.kotlin.compose.ui.theme.KotlinComposeAppTheme
import com.android.ide.common.rendering.api.SessionParams
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val MOVIE_POSTER = "https://www.example.com/image.jpg"

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
class UpcomingMovieComposableScreenshotTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig(softButtons = false),
        theme = "android:Theme.Material.Light.NoActionBar",
        renderingMode = SessionParams.RenderingMode.SHRINK,
        showSystemUi = false
    )

    @get:Rule
    val mainTestRule: MainTestRule = MainTestRule(testDispatcher = UnconfinedTestDispatcher())

    @Before
    fun setUp() {
        val engine = FakeImageLoaderEngine.Builder()
            .intercept(data = MOVIE_POSTER, drawable = ColorDrawable(Color.GREEN))
            .build()
        val imageLoader = ImageLoader.Builder(paparazzi.context)
            .error(ColorDrawable(Color.RED))
            .components { add(engine) }
            .dispatcher(dispatcher = mainTestRule.testDispatcher)
            .build()
        Coil.setImageLoader(imageLoader)
    }

    @Test
    fun testUpcomingMovieInPortraitMode() {
        paparazzi.snapshot {
            KotlinComposeAppTheme {
                UpcomingMovieComposable(
                    movieItem = createMovieItem(
                        posterPath = MOVIE_POSTER
                    ),
                    onClickMovie = {}
                )
            }
        }
    }
}
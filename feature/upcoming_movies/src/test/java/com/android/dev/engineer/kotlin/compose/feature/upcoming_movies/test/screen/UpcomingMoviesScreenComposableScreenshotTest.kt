package com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.test.screen

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import coil3.Coil
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.test.FakeImageLoaderEngine
import com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.UpcomingMoviesScreenComposable
import com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.coroutines.MainTestRule
import com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.fake.domain.MovieFake.createMovieItem
import com.android.dev.engineer.kotlin.compose.ui.theme.KotlinComposeAppTheme
import com.android.ide.common.rendering.api.SessionParams
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val MOVIE_POSTER = "https://www.example.com/image.jpg"
private val MOVIES = List(size = 15) {
    createMovieItem(posterPath = MOVIE_POSTER)
}

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
class UpcomingMoviesScreenComposableScreenshotTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig(softButtons = false),
        theme = "android:Theme.Material.Light.NoActionBar",
        renderingMode = SessionParams.RenderingMode.SHRINK,
        showSystemUi = true
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
    fun testUpcomingMovieScreen() {
        val pagingData = MutableStateFlow(PagingData.from(MOVIES))
        paparazzi.snapshot {
            KotlinComposeAppTheme {
                UpcomingMoviesScreenComposable(
                    lazyPagingItems = pagingData.collectAsLazyPagingItems(mainTestRule.testDispatcher),
                    columnSize = 3,
                    onClickMovie = {}
                )
            }
        }
    }
}
package com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.composable

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.UpcomingMoviesScreenComposable
import com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.fake.domain.MovieFake.createMovieItem
import com.android.dev.engineer.kotlin.compose.ui.theme.KotlinComposeAppTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule
import org.junit.Test

private const val COLUMN = 2
private val movies = List(size = 4) {
    createMovieItem()
}

@ExperimentalCoroutinesApi
class UpcomingMoviesScreenComposableTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testTitle() {
        val pagingData = MutableStateFlow(PagingData.from(movies))
        with(composeTestRule) {
            setContent {
                KotlinComposeAppTheme {
                    UpcomingMoviesScreenComposable(
                        lazyPagingItems = pagingData.collectAsLazyPagingItems(UnconfinedTestDispatcher()),
                        columnSize = COLUMN,
                        onClickMovie = {}
                    )
                }
            }
            onAllNodesWithTag(testTag = activity.getString(com.android.dev.engineer.kotlin.compose.ui.R.string.test_tag_upcoming_movie))
                .assertCountEquals(movies.size)
        }
    }
}
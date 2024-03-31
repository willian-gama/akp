package com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.test.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.dev.engineer.kotlin.compose.data.api.TheMovieApi.Companion.DEFAULT_PAGE_SIZE
import com.android.dev.engineer.kotlin.compose.data.domain.local.UnifiedError
import com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.UpcomingMoviesPagingSource
import com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.fake.domain.MovieFake.createUpcomingMovies
import com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.fake.use_case.GetUpcomingMoviesUseCaseFake
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UpcomingMoviesPagingSourceTest {
    private lateinit var getUpcomingMoviesUseCase: GetUpcomingMoviesUseCaseFake
    private lateinit var upcomingMoviesPagingSource: UpcomingMoviesPagingSource

    @Before
    fun setUp() {
        getUpcomingMoviesUseCase = GetUpcomingMoviesUseCaseFake()
        upcomingMoviesPagingSource = UpcomingMoviesPagingSource(getUpcomingMoviesUseCase = getUpcomingMoviesUseCase)
    }

    @Test
    fun `test initial page`() = runTest {
        getUpcomingMoviesUseCase.upcomingMovies = createUpcomingMovies(
            page = 1,
            totalPages = 10
        )

        val loadResult = upcomingMoviesPagingSource.load(
            params = PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = DEFAULT_PAGE_SIZE,
                placeholdersEnabled = true
            )
        ) as PagingSource.LoadResult.Page

        val expectedPage = PagingSource.LoadResult.Page(
            data = getUpcomingMoviesUseCase.upcomingMovies.movies,
            prevKey = null,
            nextKey = 2
        )
        assertEquals(expectedPage, loadResult)
    }

    @Test
    fun `test next page`() = runTest {
        getUpcomingMoviesUseCase.upcomingMovies = createUpcomingMovies(
            page = 2,
            totalPages = 10
        )

        val loadResult = upcomingMoviesPagingSource.load(
            params = PagingSource.LoadParams.Append(
                key = getUpcomingMoviesUseCase.upcomingMovies.page,
                loadSize = DEFAULT_PAGE_SIZE,
                placeholdersEnabled = true
            )
        ) as PagingSource.LoadResult.Page

        val expectedPage = PagingSource.LoadResult.Page(
            data = getUpcomingMoviesUseCase.upcomingMovies.movies,
            prevKey = null,
            nextKey = 3
        )
        assertEquals(expectedPage, loadResult)
    }

    @Test
    fun `test last page`() = runTest {
        getUpcomingMoviesUseCase.upcomingMovies = createUpcomingMovies(
            page = 10,
            totalPages = 10
        )
        val loadResult = upcomingMoviesPagingSource.load(
            params = PagingSource.LoadParams.Append(
                key = getUpcomingMoviesUseCase.upcomingMovies.page,
                loadSize = DEFAULT_PAGE_SIZE,
                placeholdersEnabled = true
            )
        ) as PagingSource.LoadResult.Page

        val expectedPage = PagingSource.LoadResult.Page(
            data = getUpcomingMoviesUseCase.upcomingMovies.movies,
            prevKey = null,
            nextKey = null
        )
        assertEquals(expectedPage, loadResult)
    }

    @Test
    fun `test fetch page with unified error`() = runTest {
        getUpcomingMoviesUseCase.error = UnifiedError.Http.NotFound(message = "Something went wrong")

        val loadResult = upcomingMoviesPagingSource.load(
            params = PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = DEFAULT_PAGE_SIZE,
                placeholdersEnabled = true
            )
        ) as PagingSource.LoadResult.Error

        assertEquals(getUpcomingMoviesUseCase.error, loadResult.throwable)
    }

    @Test
    fun `test refresh key`() {
        val initialPage = upcomingMoviesPagingSource.getRefreshKey(
            state = PagingState(
                pages = listOf(),
                anchorPosition = null,
                config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
                leadingPlaceholderCount = 0
            )
        )
        assertEquals(1, initialPage)
    }
}
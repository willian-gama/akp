package com.android.dev.engineer.kotlin.compose.feature.upcoming_movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.dev.engineer.kotlin.compose.data.domain.local.UnifiedError
import com.android.dev.engineer.kotlin.compose.data.domain.network.Movie
import com.android.dev.engineer.kotlin.compose.data.use_case.upcoming_movie.GetUpcomingMoviesUseCase
import timber.log.Timber

class UpcomingMoviesPagingSource(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
) : PagingSource<Int, Movie>() {
    companion object {
        const val PAGE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int {
        return PAGE
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val current = params.key ?: PAGE
            val upcomingMovie = getUpcomingMoviesUseCase.invoke(
                page = current
            )
            LoadResult.Page(
                data = upcomingMovie.movies,
                prevKey = null,
                nextKey = if (upcomingMovie.page < upcomingMovie.totalPages) {
                    current + PAGE
                } else {
                    null
                }
            )
        } catch (e: UnifiedError) {
            Timber.e(e, "Error when marking intro as complete")
            LoadResult.Error(e)
        }
    }
}
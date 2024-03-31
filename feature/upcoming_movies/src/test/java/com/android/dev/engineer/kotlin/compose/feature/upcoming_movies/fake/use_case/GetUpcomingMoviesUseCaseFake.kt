package com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.fake.use_case

import com.android.dev.engineer.kotlin.compose.data.domain.local.UnifiedError
import com.android.dev.engineer.kotlin.compose.data.domain.network.UpcomingMovies
import com.android.dev.engineer.kotlin.compose.data.use_case.upcoming_movie.GetUpcomingMoviesUseCase
import com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.fake.domain.MovieFake.createUpcomingMovies

class GetUpcomingMoviesUseCaseFake : GetUpcomingMoviesUseCase {
    var upcomingMovies = createUpcomingMovies(
        page = 10,
        totalPages = 10
    )
    var error: UnifiedError? = null

    override suspend fun invoke(page: Int): UpcomingMovies {
        error?.let { throw it }
        return upcomingMovies
    }
}
package com.android.dev.engineer.kotlin.compose.fake.use_case

import com.android.dev.engineer.kotlin.compose.data.domain.local.UnifiedError
import com.android.dev.engineer.kotlin.compose.data.domain.network.UpcomingMovies
import com.android.dev.engineer.kotlin.compose.data.use_case.upcoming_movie.GetUpcomingMoviesUseCase
import com.android.dev.engineer.kotlin.compose.fake.domain.MovieFake.createUpcomingMovies

class GetUpcomingMoviesUseCaseFake : GetUpcomingMoviesUseCase {
    var upcomingMovies = createUpcomingMovies(10, 10)
    var error: UnifiedError? = null

    override suspend fun invoke(page: Int): UpcomingMovies {
        error?.let { throw it }
        return upcomingMovies
    }
}
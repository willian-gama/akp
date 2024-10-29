package com.android.dev.engineer.kotlin.compose.data.api.movie

import com.android.dev.engineer.kotlin.compose.data.domain.network.UpcomingMovies
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {
    override suspend fun getUpcomingMovies(page: Int): UpcomingMovies {
        return movieApi.getUpcomingMovies(page)
    }
}
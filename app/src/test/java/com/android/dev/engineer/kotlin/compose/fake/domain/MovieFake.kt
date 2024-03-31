package com.android.dev.engineer.kotlin.compose.fake.domain

import com.android.dev.engineer.kotlin.compose.data.domain.network.Movie
import com.android.dev.engineer.kotlin.compose.data.domain.network.UpcomingMovies

object MovieFake {
    private fun createMovie(
        id: Int = 1,
        originalTitle: String = "Original title",
        posterPath: String = "/r16LpvYoE6ADjbG",
        releaseDate: String = "2016-21-03",
        voteAverage: Double = 8.5
    ) = Movie(
        id = id,
        originalTitle = originalTitle,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )

    fun createUpcomingMovies(
        page: Int = 1,
        totalPages: Int = 10
    ) = UpcomingMovies(
        page = page,
        movies = listOf(
            createMovie()
        ),
        totalPages = totalPages
    )
}
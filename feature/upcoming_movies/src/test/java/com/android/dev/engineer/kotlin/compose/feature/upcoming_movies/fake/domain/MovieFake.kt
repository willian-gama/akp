package com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.fake.domain

import com.android.dev.engineer.kotlin.compose.data.api.TheMovieApi.Companion.IMAGE_URL
import com.android.dev.engineer.kotlin.compose.data.domain.local.MovieItem
import com.android.dev.engineer.kotlin.compose.data.domain.network.Movie
import com.android.dev.engineer.kotlin.compose.data.domain.network.UpcomingMovies

object MovieFake {
    fun createMovieItem(
        id: Int = 1,
        originalTitle: String = "Original title",
        posterPath: String = "r16LpvYoE6ADjbG",
        releaseDate: String = "July 19, 2023",
        voteAverage: Double = 8.5
    ) = MovieItem(
        id = id,
        originalTitle = originalTitle,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )

    fun createMovie(
        id: Int = 1,
        originalTitle: String = "Original title",
        posterPath: String = "$IMAGE_URL/r16LpvYoE6ADjbG",
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
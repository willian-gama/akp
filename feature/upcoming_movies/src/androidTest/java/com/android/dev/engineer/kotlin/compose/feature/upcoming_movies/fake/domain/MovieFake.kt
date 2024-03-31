package com.android.dev.engineer.kotlin.compose.feature.upcoming_movies.fake.domain

import com.android.dev.engineer.kotlin.compose.data.domain.local.MovieItem

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
}
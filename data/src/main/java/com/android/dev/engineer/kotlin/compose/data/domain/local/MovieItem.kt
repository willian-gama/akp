package com.android.dev.engineer.kotlin.compose.data.domain.local

import com.android.dev.engineer.kotlin.compose.data.api.TheMovieApi.Companion.IMAGE_URL
import com.android.dev.engineer.kotlin.compose.data.domain.network.Movie
import com.android.dev.engineer.kotlin.compose.data.extension.toDateFormatted

data class MovieItem(
    val id: Int,
    val originalTitle: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double
)

fun Movie.toMovieItem() = MovieItem(
    id = id,
    originalTitle = originalTitle,
    posterPath = posterPath?.let { IMAGE_URL + it }.orEmpty(),
    releaseDate = releaseDate.toDateFormatted(),
    voteAverage = voteAverage
)
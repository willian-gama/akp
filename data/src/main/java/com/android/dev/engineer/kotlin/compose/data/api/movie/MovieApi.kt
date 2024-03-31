package com.android.dev.engineer.kotlin.compose.data.api.movie

import com.android.dev.engineer.kotlin.compose.data.domain.network.UpcomingMovies
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int
    ): UpcomingMovies
}
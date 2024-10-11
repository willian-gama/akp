package com.android.dev.engineer.kotlin.compose.data.di

import com.android.dev.engineer.kotlin.compose.data.api.TheMovieApi
import com.android.dev.engineer.kotlin.compose.data.api.authentication.AuthenticationApi
import com.android.dev.engineer.kotlin.compose.data.api.movie.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun provideAuthenticationApi(theMovieApi: TheMovieApi): AuthenticationApi {
        return theMovieApi
    }

    @Singleton
    @Provides
    fun provideMovieApi(theMovieApi: TheMovieApi): MovieApi {
        return theMovieApi
    }
}
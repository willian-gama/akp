package com.android.dev.engineer.kotlin.compose.data.domain.local

sealed class MainNavGraph(val route: String) {
    data object Intro : MainNavGraph(route = "intro")
    data object SignIn : MainNavGraph(route = "sign_in")
    data object UpcomingMovies : MainNavGraph(route = "upcoming_movies")
    data object Movie : MainNavGraph(route = "movie")
}
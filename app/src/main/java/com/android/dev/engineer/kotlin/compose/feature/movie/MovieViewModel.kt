package com.android.dev.engineer.kotlin.compose.feature.movie

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.android.dev.engineer.kotlin.compose.feature.main.MovieArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        const val ID_ARG = "id"
    }

    val movieArgs = savedStateHandle.toRoute<MovieArgs>()
}
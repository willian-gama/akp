package com.android.dev.engineer.kotlin.compose.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage

@Composable
fun AsyncImageComposable(
    modifier: Modifier = Modifier,
    url: String,
    contentDescription: String
) {
    AsyncImage(
        modifier = modifier,
        model = url,
        contentDescription = contentDescription
    )
}
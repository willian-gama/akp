package com.android.dev.engineer.kotlin.compose.feature.upcoming_movies

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.dev.engineer.kotlin.compose.data.domain.local.MovieItem
import com.android.dev.engineer.kotlin.compose.ui.composable.AsyncImageComposable

private const val MOVIE_POSTER_RATIO = 2f / 3f
private const val ALPHA_IN_SIXTY_PERCENT = 0.7f

@Composable
fun UpcomingMovieComposable(
    movieItem: MovieItem,
    onClickMovie: () -> Unit
) {
    Card(
        modifier = Modifier
            .testTag(tag = stringResource(id = com.android.dev.engineer.kotlin.compose.ui.R.string.test_tag_upcoming_movie))
            .aspectRatio(ratio = MOVIE_POSTER_RATIO)
            .clickable { onClickMovie() },
        backgroundColor = colorResource(id = com.android.dev.engineer.kotlin.compose.ui.R.color.grey),
        border = BorderStroke(
            width = 1.dp,
            color = colorResource(id = com.android.dev.engineer.kotlin.compose.ui.R.color.black)
        )
    ) {
        Box {
            AsyncImageComposable(
                modifier = Modifier.matchParentSize(),
                url = movieItem.posterPath,
                contentDescription = movieItem.originalTitle
            )
            if (movieItem.voteAverage > 0) {
                Text(
                    modifier = Modifier
                        .align(alignment = Alignment.TopEnd)
                        .clip(shape = RoundedCornerShape(percent = 15))
                        .background(color = colorResource(id = com.android.dev.engineer.kotlin.compose.ui.R.color.black).copy(alpha = ALPHA_IN_SIXTY_PERCENT))
                        .padding(horizontal = 4.dp),
                    text = movieItem.voteAverage.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    color = colorResource(id = com.android.dev.engineer.kotlin.compose.ui.R.color.white)
                )
            }
            Column(
                modifier = Modifier
                    .background(color = colorResource(id = com.android.dev.engineer.kotlin.compose.ui.R.color.black).copy(alpha = ALPHA_IN_SIXTY_PERCENT))
                    .padding(all = 4.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = movieItem.originalTitle,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = com.android.dev.engineer.kotlin.compose.ui.R.color.white),
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = movieItem.releaseDate,
                    color = colorResource(id = com.android.dev.engineer.kotlin.compose.ui.R.color.white),
                    fontSize = MaterialTheme.typography.body2.fontSize
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMovieItemComposable() {
    UpcomingMovieComposable(
        movieItem = MovieItem(
            id = 1,
            originalTitle = "Transformers: Rise of the Beasts",
            posterPath = "/r16LpvYoE6ADjbG",
            releaseDate = "2022-01-12",
            voteAverage = 9.0
        ),
        onClickMovie = {}
    )
}
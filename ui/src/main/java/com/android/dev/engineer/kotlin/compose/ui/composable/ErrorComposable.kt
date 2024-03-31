package com.android.dev.engineer.kotlin.compose.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.android.dev.engineer.kotlin.compose.ui.R
import com.android.dev.engineer.kotlin.compose.ui.util.ExcludeFromJacocoGeneratedReport

@Composable
fun ErrorComposable(
    modifier: Modifier = Modifier,
    message: String,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = modifier
            .testTag(tag = stringResource(id = R.string.test_tag_error))
            .background(color = colorResource(id = R.color.white)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.testTag(tag = stringResource(id = R.string.test_tag_error_message)),
            text = message,
            fontWeight = FontWeight.Bold
        )
        ButtonComposable(
            text = stringResource(id = R.string.label_try_again),
            onClick = {
                onRetryClick()
            }
        )
    }
}

@ExcludeFromJacocoGeneratedReport
@Preview
@Composable
private fun PreviewErrorComposable() {
    ErrorComposable(
        modifier = Modifier.fillMaxSize(),
        message = "Something went wrong",
        onRetryClick = {}
    )
}
package com.android.dev.engineer.kotlin.compose.feature.intro

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.android.dev.engineer.kotlin.compose.data.domain.local.MainNavGraph
import com.android.dev.engineer.kotlin.compose.ui.R
import com.android.dev.engineer.kotlin.compose.ui.composable.ButtonComposable
import com.android.dev.engineer.kotlin.compose.ui.composable.PagerIndicatorComposable
import com.android.dev.engineer.kotlin.compose.ui.composable.TextButtonComposable
import com.android.dev.engineer.kotlin.compose.ui.theme.KotlinComposeAppTheme
import com.android.dev.engineer.kotlin.compose.ui.util.ExcludeFromJacocoGeneratedReport
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun IntroScreen(
    viewModel: IntroViewModel = hiltViewModel(),
    onSkipClicked: (MainNavGraph) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val introItems = listOf(
        IntroItem(title = "Page 1", imageVector = Icons.Filled.Call),
        IntroItem(title = "Page 2", imageVector = Icons.Filled.Search),
        IntroItem(title = "Page 3", imageVector = Icons.Filled.Info),
        IntroItem(title = "Page 4", imageVector = Icons.Filled.Check)
    )

    LaunchedEffect(Unit) {
        with(lifecycleOwner) {
            lifecycleScope.launch {
                viewModel.effect.flowWithLifecycle(lifecycle).collectLatest { mainNavGraph ->
                    onSkipClicked(mainNavGraph)
                }
            }
        }
    }

    IntroScreenComposable(
        introItems = introItems,
        onGetStartedAction = { viewModel.markIntroComplete() }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun IntroScreenComposable(
    introItems: List<IntroItem>,
    onGetStartedAction: () -> Unit
) {
    KotlinComposeAppTheme {
        val pagerState = rememberPagerState(
            pageCount = { introItems.size }
        )
        val coroutineScope = rememberCoroutineScope()
        Box(
            modifier = Modifier
                .testTag(tag = stringResource(id = R.string.test_tag_intro_screen))
                .fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState,
                pageContent = { pageIndex ->
                    with(introItems[pageIndex]) {
                        Column(
                            modifier = Modifier
                                .padding(all = 16.dp)
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier.padding(bottom = 8.dp),
                                contentDescription = title,
                                imageVector = imageVector,
                                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary)
                            )
                            Text(
                                text = title,
                                style = MaterialTheme.typography.h5,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.primary
                            )

                            if (pageIndex == introItems.lastIndex) {
                                ButtonComposable(
                                    modifier = Modifier.padding(top = 8.dp),
                                    text = "Get started",
                                    onClick = { onGetStartedAction() }
                                )
                            }
                        }
                    }
                }
            )

            TextButtonComposable(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .align(alignment = Alignment.TopEnd),
                text = "Skip",
                onClick = { onGetStartedAction() }
            )

            PagerIndicatorComposable(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(alignment = Alignment.BottomCenter),
                selectedIndex = pagerState.currentPage,
                total = introItems.size,
                onClick = { index ->
                    coroutineScope.launch { pagerState.scrollToPage(index) }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
@ExcludeFromJacocoGeneratedReport
private fun PreviewIntroScreen() {
    IntroScreenComposable(
        introItems = listOf(
            IntroItem(title = "Page 1", imageVector = Icons.Filled.Call),
            IntroItem(title = "Page 2", imageVector = Icons.Filled.Search),
            IntroItem(title = "Page 3", imageVector = Icons.Filled.Info),
            IntroItem(title = "Page 4", imageVector = Icons.Filled.Check)
        ),
        onGetStartedAction = {}
    )
}
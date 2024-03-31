package com.android.dev.engineer.kotlin.compose.test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.android.dev.engineer.kotlin.compose.hilt.HiltTestActivity
import com.android.dev.engineer.kotlin.compose.data.domain.local.MainNavGraph
import com.android.dev.engineer.kotlin.compose.feature.main.MainNavHostComposable
import com.android.dev.engineer.kotlin.compose.ui.theme.KotlinComposeAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainNavHostComposableTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testIntroRoute() {
        with(composeTestRule) {
            setContent {
                KotlinComposeAppTheme {
                    MainNavHostComposable(
                        startDestination = MainNavGraph.Intro.route
                    )
                }
            }
            onNodeWithTag(testTag = activity.getString(com.android.dev.engineer.kotlin.compose.ui.R.string.test_tag_intro_screen)).assertIsDisplayed()
        }
    }
}
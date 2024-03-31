package com.android.dev.engineer.kotlin.compose.test.viewmodel

import com.android.dev.engineer.kotlin.compose.coroutines.MainTestRule
import com.android.dev.engineer.kotlin.compose.data.domain.local.MainNavGraph
import com.android.dev.engineer.kotlin.compose.fake.use_case.GetInitialRouteUseCaseFake
import com.android.dev.engineer.kotlin.compose.feature.routing.RoutingViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class RoutingViewModelTest {
    @get:Rule
    val mainTestRule: MainTestRule = MainTestRule()
    private lateinit var getInitialRouteUseCase: GetInitialRouteUseCaseFake
    private fun createViewModel(): RoutingViewModel {
        return RoutingViewModel(getInitialRouteUseCase = getInitialRouteUseCase)
    }

    @Before
    fun setUp() {
        getInitialRouteUseCase = GetInitialRouteUseCaseFake()
    }

    @Test
    fun `test initial navigation as intro`() = runTest {
        val expectedNavGraph = MainNavGraph.Intro
        getInitialRouteUseCase.mainNavGraph = expectedNavGraph
        assertEquals(expectedNavGraph, createViewModel().effect.first())
    }

    @Test
    fun `test initial navigation as sign in`() = runTest {
        val expectedNavGraph = MainNavGraph.SignIn
        getInitialRouteUseCase.mainNavGraph = expectedNavGraph
        assertEquals(expectedNavGraph, createViewModel().effect.first())
    }

    @Test
    fun `test when initial navigation fails and redirect to sign in`() = runTest {
        getInitialRouteUseCase.error = IOException()
        assertEquals(MainNavGraph.SignIn, createViewModel().effect.first())
    }
}
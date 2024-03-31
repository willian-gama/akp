package com.android.dev.engineer.kotlin.compose.data.test.api

import com.android.dev.engineer.kotlin.compose.data.api.movie.MovieApi
import com.android.dev.engineer.kotlin.compose.data.domain.local.UnifiedError
import com.android.dev.engineer.kotlin.compose.data.domain.network.ApiError
import com.android.dev.engineer.kotlin.compose.data.domain.network.UpcomingMovies
import com.android.dev.engineer.kotlin.compose.data.extension.MoshiAdapterExt.fromJson
import com.android.dev.engineer.kotlin.compose.data.extension.toTheMovieApi
import com.android.dev.engineer.kotlin.compose.data.fake.use_case.ApiErrorHandlingUseCaseFake
import com.android.dev.engineer.kotlin.compose.data.util.FileUtil
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection

private const val TEST_PAGE = 1

@ExperimentalCoroutinesApi
class TheMovieApiTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var movieApi: MovieApi
    private lateinit var apiErrorHandlingUseCase: ApiErrorHandlingUseCaseFake

    @Before
    fun setUp() {
        apiErrorHandlingUseCase = ApiErrorHandlingUseCaseFake()
        mockWebServer = MockWebServer()
        mockWebServer.start()
        movieApi = mockWebServer.toTheMovieApi(apiErrorHandlingUseCase = apiErrorHandlingUseCase)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `verify get upcoming movies successfully`() = runTest {
        val newSessionJson = FileUtil.getContent(file = "json/upcoming_movies.json")
        val mockResponse = MockResponse().setBody(newSessionJson)
        mockWebServer.enqueue(response = mockResponse)
        val upcomingMovies = movieApi.getUpcomingMovies(page = TEST_PAGE)

        val request = mockWebServer.takeRequest()
        val requestUrl = request.requestUrl!!
        val queryParameterNames = requestUrl.queryParameterNames.toList()

        assertEquals("GET", request.method)
        assertEquals("/movie/upcoming", requestUrl.encodedPath)
        with(queryParameterNames) {
            assertEquals(2, size)
            assertEquals("page", get(0))
            assertEquals("api_key", get(1))
        }
        assertEquals(newSessionJson.fromJson<UpcomingMovies>(), upcomingMovies)
    }

    @Test(expected = UnifiedError.Http.NotFound::class)
    fun `test get upcoming movies not authorized`() = runTest {
        val apiError = FileUtil.getContent(file = "json/invalid_api_key.json").fromJson<ApiError>()
        apiErrorHandlingUseCase.unifiedError = UnifiedError.Http.NotFound(message = apiError.message)

        val newSessionJson = FileUtil.getContent(file = "json/new_session.json")
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED)
            .setBody(newSessionJson)

        mockWebServer.enqueue(response = mockResponse)
        movieApi.getUpcomingMovies(page = TEST_PAGE)
    }
}
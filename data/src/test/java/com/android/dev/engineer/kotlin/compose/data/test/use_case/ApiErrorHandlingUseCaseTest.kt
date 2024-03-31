package com.android.dev.engineer.kotlin.compose.data.test.use_case

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.android.dev.engineer.kotlin.compose.data.R
import com.android.dev.engineer.kotlin.compose.data.di.NetworkModule.provideMoshi
import com.android.dev.engineer.kotlin.compose.data.domain.local.UnifiedError
import com.android.dev.engineer.kotlin.compose.data.domain.network.ApiError
import com.android.dev.engineer.kotlin.compose.data.extension.MoshiAdapterExt.toJson
import com.android.dev.engineer.kotlin.compose.data.use_case.api_error_handling.ApiErrorHandlingUseCaseImpl
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.internal.EMPTY_RESPONSE
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.SocketException
import java.net.UnknownHostException

private const val TEST_ERROR_MESSAGE = "Error message"

@RunWith(RobolectricTestRunner::class)
class ApiErrorHandlingUseCaseTest {
    private val context: Context = ApplicationProvider.getApplicationContext()
    private lateinit var apiErrorHandlingUseCaseImpl: ApiErrorHandlingUseCaseImpl

    @Before
    fun setUp() {
        apiErrorHandlingUseCaseImpl = ApiErrorHandlingUseCaseImpl(
            context = context,
            moshi = provideMoshi()
        )
    }

    @Test
    fun `test invalid response with generic http exception`() {
        val throwable = HttpException(Response.error<ResponseBody>(HttpURLConnection.HTTP_NOT_FOUND, EMPTY_RESPONSE))
        val expectedUnifiedError = UnifiedError.Http.NotFound(message = context.getString(R.string.label_something_went_wrong))
        val unifiedError = apiErrorHandlingUseCaseImpl.invoke(throwable)
        assertEquals(expectedUnifiedError, unifiedError)
    }

    @Test
    fun `test not found http exception`() {
        val responseBody = ApiError(message = TEST_ERROR_MESSAGE).toJson().toResponseBody()
        val throwable = HttpException(Response.error<ResponseBody>(HttpURLConnection.HTTP_NOT_FOUND, responseBody))
        val expectedUnifiedError = UnifiedError.Http.NotFound(message = TEST_ERROR_MESSAGE)
        val unifiedError = apiErrorHandlingUseCaseImpl.invoke(throwable)
        assertEquals(expectedUnifiedError, unifiedError)
    }

    @Test
    fun `test unauthorized http exception`() {
        val responseBody = ApiError(message = TEST_ERROR_MESSAGE).toJson().toResponseBody()
        val throwable = HttpException(Response.error<ResponseBody>(HttpURLConnection.HTTP_UNAUTHORIZED, responseBody))
        val expectedUnifiedError = UnifiedError.Http.Unauthorized(message = TEST_ERROR_MESSAGE)
        val unifiedError = apiErrorHandlingUseCaseImpl.invoke(throwable)
        assertEquals(expectedUnifiedError, unifiedError)
    }

    @Test
    fun `test generic http exception`() {
        val throwable = HttpException(Response.error<ResponseBody>(HttpURLConnection.HTTP_GONE, EMPTY_RESPONSE))
        val expectedUnifiedError = UnifiedError.Generic(message = context.getString(R.string.label_something_went_wrong))
        val unifiedError = apiErrorHandlingUseCaseImpl.invoke(throwable)
        assertEquals(expectedUnifiedError, unifiedError)
    }

    @Test
    fun `test host unreachable io exception`() {
        val throwable = UnknownHostException()
        val expectedUnifiedError = UnifiedError.Connectivity.HostUnreachable(message = context.getString(R.string.label_there_is_an_issue_with_the_network_connection))
        val unifiedError = apiErrorHandlingUseCaseImpl.invoke(throwable)
        assertEquals(expectedUnifiedError, unifiedError)
    }

    @Test
    fun `test generic io exception`() {
        val throwable = SocketException()
        val expectedUnifiedError = UnifiedError.Generic(message = context.getString(R.string.label_something_went_wrong))
        val unifiedError = apiErrorHandlingUseCaseImpl.invoke(throwable)
        assertEquals(expectedUnifiedError, unifiedError)
    }

    @Test
    fun `test generic exception`() {
        val throwable = NullPointerException()
        val expectedUnifiedError = UnifiedError.Generic(message = context.getString(R.string.label_something_went_wrong))
        val unifiedError = apiErrorHandlingUseCaseImpl.invoke(throwable)
        assertEquals(expectedUnifiedError, unifiedError)
    }
}
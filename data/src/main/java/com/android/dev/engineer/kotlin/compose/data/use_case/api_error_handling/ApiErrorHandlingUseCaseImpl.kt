package com.android.dev.engineer.kotlin.compose.data.use_case.api_error_handling

import android.content.Context
import com.android.dev.engineer.kotlin.compose.data.R
import com.android.dev.engineer.kotlin.compose.data.domain.local.UnifiedError
import com.android.dev.engineer.kotlin.compose.data.domain.network.ApiError
import com.android.dev.engineer.kotlin.compose.data.extension.fromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.HttpURLConnection
import java.net.UnknownHostException
import javax.inject.Inject

class ApiErrorHandlingUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi
) : ApiErrorHandlingUseCase {
    override fun invoke(throwable: Throwable): UnifiedError {
        return when (throwable) {
            is HttpException -> throwable.handleError()
            is IOException -> throwable.handleError()
            else -> UnifiedError.Generic(message = context.getString(R.string.label_something_went_wrong))
        }
    }

    private fun HttpException.handleError(): UnifiedError {
        val errorBody = response()?.errorBody()?.string()
        val message = try {
            if (!errorBody.isNullOrBlank()) {
                moshi.fromJson<ApiError>(json = errorBody)?.message.orEmpty()
            } else {
                ""
            }
        } catch (e: JsonDataException) {
            Timber.e("Error when parsing json $e")
            ""
        }.ifEmpty {
            context.getString(R.string.label_something_went_wrong)
        }
        return when (code()) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> UnifiedError.Http.Unauthorized(message = message)
            HttpURLConnection.HTTP_NOT_FOUND -> UnifiedError.Http.NotFound(message = message)
            else -> UnifiedError.Generic(message = message)
        }
    }

    private fun IOException.handleError(): UnifiedError {
        return when (this) {
            is UnknownHostException -> UnifiedError.Connectivity.HostUnreachable(message = context.getString(R.string.label_there_is_an_issue_with_the_network_connection))
            else -> UnifiedError.Generic(message = context.getString(R.string.label_something_went_wrong))
        }
    }
}
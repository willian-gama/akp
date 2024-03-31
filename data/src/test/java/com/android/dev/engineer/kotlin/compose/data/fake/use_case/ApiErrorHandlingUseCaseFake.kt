package com.android.dev.engineer.kotlin.compose.data.fake.use_case

import com.android.dev.engineer.kotlin.compose.data.domain.local.UnifiedError
import com.android.dev.engineer.kotlin.compose.data.use_case.api_error_handling.ApiErrorHandlingUseCase

class ApiErrorHandlingUseCaseFake : ApiErrorHandlingUseCase {
    lateinit var unifiedError: UnifiedError

    override fun invoke(throwable: Throwable): UnifiedError {
        return unifiedError
    }
}
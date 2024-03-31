package com.android.dev.engineer.kotlin.compose.data.use_case.api_error_handling

import com.android.dev.engineer.kotlin.compose.data.domain.local.UnifiedError

interface ApiErrorHandlingUseCase {
    operator fun invoke(throwable: Throwable): UnifiedError
}
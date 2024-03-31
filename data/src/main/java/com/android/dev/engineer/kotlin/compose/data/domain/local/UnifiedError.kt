package com.android.dev.engineer.kotlin.compose.data.domain.local

sealed class UnifiedError(
    override val message: String
) : Exception() {
    data class Generic(override val message: String) : Connectivity(message = message)

    sealed class Http(message: String) : UnifiedError(message = message) {
        data class Unauthorized(override val message: String) : Http(message = message)
        data class NotFound(override val message: String) : Http(message = message)
    }

    sealed class Connectivity(message: String) : UnifiedError(message = message) {
        data class HostUnreachable(override val message: String) : Connectivity(message = message)
    }
}
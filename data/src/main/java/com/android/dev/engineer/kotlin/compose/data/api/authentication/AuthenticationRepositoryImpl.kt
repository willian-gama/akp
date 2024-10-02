package com.android.dev.engineer.kotlin.compose.data.api.authentication

import com.android.dev.engineer.kotlin.compose.data.domain.network.Authenticated
import com.android.dev.engineer.kotlin.compose.data.domain.network.LogOut
import com.android.dev.engineer.kotlin.compose.data.domain.network.NewSession
import com.android.dev.engineer.kotlin.compose.data.domain.network.Session
import com.android.dev.engineer.kotlin.compose.data.domain.network.SignIn
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationApi: AuthenticationApi
) : AuthenticationRepository {
    override suspend fun getRequestToken(): Authenticated {
        return authenticationApi.getRequestToken()
    }

    override suspend fun login(signIn: SignIn): Authenticated {
        return authenticationApi.login(signIn = signIn)
    }

    override suspend fun getNewSession(session: Session): NewSession {
        return authenticationApi.getNewSession(session = session)
    }

    override suspend fun logOut(logOut: LogOut) {
        authenticationApi.logOut(logOut)
    }
}
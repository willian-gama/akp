package com.android.dev.engineer.kotlin.compose.data.test.shared_preferences

import androidx.core.content.edit
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.dev.engineer.kotlin.compose.data.di.SharedPrefsModule.provideEncryptedSharedPreferences
import com.android.dev.engineer.kotlin.compose.data.shared_preferences.UserEncryptedSharedPrefsImpl
import com.android.dev.engineer.kotlin.compose.data.shared_preferences.UserEncryptedSharedPrefsImpl.Companion.SESSION_ID_KEY
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val FAKE_SESSION_ID = "fake_session_id"

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class UserEncryptedSharedPrefsTest {
    private lateinit var userEncryptedSharedPrefs: UserEncryptedSharedPrefsImpl
    private var sharedPreferences = spyk(
        provideEncryptedSharedPreferences(context = ApplicationProvider.getApplicationContext())
    )

    @Before
    fun setUp() {
        userEncryptedSharedPrefs = UserEncryptedSharedPrefsImpl(
            sharedPreferences = sharedPreferences
        )
    }

    @After
    fun tearDown() {
        sharedPreferences.edit {
            clear()
        }
    }

    @Test
    fun testGetCachedSessionId() = runTest {
        userEncryptedSharedPrefs.saveSessionId(sessionId = FAKE_SESSION_ID)
        assertEquals(FAKE_SESSION_ID, userEncryptedSharedPrefs.getSessionId())
        // Issue in ReactiveCircus - Github action
        //verify(exactly = 0) { sharedPreferences.getString(FAKE_SESSION_ID, "") }
    }

    @Test
    fun testGetSessionIdNotCached() = runTest {
        sharedPreferences.edit {
            putString(SESSION_ID_KEY, FAKE_SESSION_ID)
        }
        repeat(times = 2) {
            assertEquals(FAKE_SESSION_ID, userEncryptedSharedPrefs.getSessionId())
        }
        // Issue in ReactiveCircus - Github action
        verify(exactly = 1) { sharedPreferences.getString(SESSION_ID_KEY, "") }
    }

    @Test
    fun testSessionIdIsCleared() = runTest {
        userEncryptedSharedPrefs.saveSessionId(sessionId = FAKE_SESSION_ID)
        userEncryptedSharedPrefs.clearAll()
        assertEquals("", userEncryptedSharedPrefs.getSessionId())
    }
}
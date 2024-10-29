package com.android.dev.engineer.kotlin.compose.data.shared_preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import com.android.dev.engineer.kotlin.compose.data.di.EncryptedSharedPrefsKey

// Use device file explorer to find it: data/data/com.android.dev.engineer.kotlin.compose/shared_prefs/encrypted_shared_preferences.xml
class UserEncryptedSharedPrefsImpl(
    @EncryptedSharedPrefsKey val sharedPreferences: SharedPreferences
) : UserEncryptedSharedPrefs {
    private val cachedUserSharedPrefs = HashMap<String, String>()

    override fun saveSessionId(sessionId: String) {
        cachedUserSharedPrefs[SESSION_ID_KEY] = sessionId
        sharedPreferences.edit {
            putString(SESSION_ID_KEY, sessionId)
        }
    }

    override fun getSessionId(): String {
        return cachedUserSharedPrefs.getOrPut(SESSION_ID_KEY) {
            sharedPreferences.getString(SESSION_ID_KEY, "").orEmpty()
        }
    }

    override fun clearAll() {
        cachedUserSharedPrefs.clear()
        sharedPreferences.edit {
            clear()
        }
    }

    companion object {
        const val SESSION_ID_KEY = "session_id_key"
    }
}
package com.example.new_lto_app

import android.content.Context
import com.example.new_lto_app.network.AuthResponse

// Remembers on the phone who is logged in (or that the user chose offline mode).
// Stored in the app's private SharedPreferences, so it survives closing the app.
class SessionManager(context: Context) {

    private val prefs = context.applicationContext
        .getSharedPreferences("lto_session", Context.MODE_PRIVATE)

    fun saveLogin(auth: AuthResponse) {
        prefs.edit()
            .putString(KEY_TOKEN, auth.token)
            .putInt(KEY_USER_ID, auth.user.userId)
            .putString(KEY_NAME, auth.user.fullName)
            .putString(KEY_EMAIL, auth.user.email)
            .putString(KEY_LICENSE, auth.user.targetLicense)
            .putBoolean(KEY_OFFLINE, false)
            .apply()
    }

    fun continueOffline() {
        prefs.edit().putBoolean(KEY_OFFLINE, true).apply()
    }

    fun logout() {
        prefs.edit().clear().apply()
    }

    val token: String? get() = prefs.getString(KEY_TOKEN, null)
    val isLoggedIn: Boolean get() = token != null
    val isOfflineMode: Boolean get() = prefs.getBoolean(KEY_OFFLINE, false)
    val userId: Int get() = prefs.getInt(KEY_USER_ID, -1)
    val fullName: String? get() = prefs.getString(KEY_NAME, null)
    val email: String? get() = prefs.getString(KEY_EMAIL, null)
    val targetLicense: String? get() = prefs.getString(KEY_LICENSE, null)

    // True once the user has either logged in or tapped "Continue Offline".
    fun hasChosenMode(): Boolean = isLoggedIn || isOfflineMode

    companion object {
        private const val KEY_TOKEN = "token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_NAME = "full_name"
        private const val KEY_EMAIL = "email"
        private const val KEY_LICENSE = "target_license"
        private const val KEY_OFFLINE = "offline_mode"
    }
}

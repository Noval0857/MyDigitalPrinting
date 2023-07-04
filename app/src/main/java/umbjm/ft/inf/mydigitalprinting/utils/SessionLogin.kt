package umbjm.ft.inf.mydigitalprinting.utils

import android.content.Context
import android.content.SharedPreferences

class SessionLogin(context: Context) {
    companion object {
        private const val PREF_NAME = "LoginPrefs"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    var email: String?
        get() = sharedPreferences.getString(KEY_EMAIL, null)
        set(value) = sharedPreferences.edit().putString(KEY_EMAIL, value).apply()

    var password: String?
        get() = sharedPreferences.getString(KEY_PASSWORD, null)
        set(value) = sharedPreferences.edit().putString(KEY_PASSWORD, value).apply()

    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()

    fun saveLoginDetails(email: String, password: String) {
        this.email = email
        this.password = password
        isLoggedIn = true
    }

    fun clearLoginDetails() {
        email = null
        password = null
        isLoggedIn = false
    }
}

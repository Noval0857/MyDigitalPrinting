//package umbjm.ft.inf.mydigitalprinting.utils
//
//import android.content.Context
//import android.content.SharedPreferences
//import umbjm.ft.inf.mydigitalprinting.model.UserModel
//
//class SessionLogin(context: Context) {
//    companion object {
//        private const val PREF_NAME = "LoginPrefs"
//        private const val ID = "id"
//        private const val EMAIL = "email"
//        private const val PASSWORD = "password"
//        private const val IS_LOGGED_IN = "isLoggedIn"
//    }
//
//    private val sharedPreferences: SharedPreferences =
//        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//    private val editor: SharedPreferences.Editor
//        get() = sharedPreferences.edit()
//
//    var id: String?
//        get() = sharedPreferences.getString(ID, null)
//        set(value) = sharedPreferences.edit().putString(ID, value).apply()
//
//    var email: String?
//        get() = sharedPreferences.getString(EMAIL, null)
//        set(value) = sharedPreferences.edit().putString(EMAIL, value).apply()
//
//    var password: String?
//        get() = sharedPreferences.getString(PASSWORD, null)
//        set(value) = sharedPreferences.edit().putString(PASSWORD, value).apply()
//
//    var isLoggedIn: Boolean
//        get() = sharedPreferences.getBoolean(IS_LOGGED_IN, false)
//        set(value) = sharedPreferences.edit().putBoolean(IS_LOGGED_IN, value).apply()
//
//    fun saveLoginDetails(email: String, password: String) {
//        this.email = email
//        this.password = password
//        isLoggedIn = true
//    }
//
//    fun createLoginSession(user: UserModel) {
//        val editor = sharedPreferences.edit()
//        editor.putBoolean(IS_LOGGED_IN, true)
//        user.id?.let { editor.putInt(ID, it) }
//        editor.putString(PASSWORD, user.password)
//        editor.putString(EMAIL, user.email)
//        editor.apply()
//    }
//
//    fun clearLoginDetails() {
//        email = null
//        password = null
//        isLoggedIn = false
//    }
//}

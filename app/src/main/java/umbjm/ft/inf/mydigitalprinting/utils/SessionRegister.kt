//package umbjm.ft.inf.mydigitalprinting.utils
//
//import android.content.Context
//import android.content.SharedPreferences
//
//class SessionRegister(context: Context) {
//    lateinit var error: String
//    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
//        PREF_NAME,
//        Context.MODE_PRIVATE
//    )
//    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
//
//    var email: String?
//        get() = sharedPreferences.getString(KEY_EMAIL, null)
//        set(value) = sharedPreferences.edit().putString(KEY_EMAIL, value).apply()
//
//    var password: String?
//        get() = sharedPreferences.getString(KEY_PASSWORD, null)
//        set(value) = sharedPreferences.edit().putString(KEY_PASSWORD, value).apply()
//
//    var nama: String?
//        get() = sharedPreferences.getString(KEY_NAMA, null)
//        set(value) = sharedPreferences.edit().putString(KEY_NAMA, value).apply()
//
//    var address: String?
//        get() = sharedPreferences.getString(KEY_ADDRESS, null)
//        set(value) = sharedPreferences.edit().putString(KEY_ADDRESS, value).apply()
//
//    var confirmPassword: String?
//        get() = sharedPreferences.getString(KEY_CONFIRM_PASSWORD, null)
//        set(value) = sharedPreferences.edit().putString(KEY_CONFIRM_PASSWORD, value).apply()
//
//
//    var isRegistered: Boolean
//        get() = sharedPreferences.getBoolean(KEY_REGISTERED, false)
//        set(value) {
//            editor.putBoolean(KEY_REGISTERED, value)
//            editor.apply()
//        }
//
//    companion object {
//        private const val PREF_NAME = "RegistrationSession"
//        private const val KEY_NAMA = "nama"
//        private const val KEY_ADDRESS = "address"
//        private const val KEY_EMAIL = "email"
//        private const val KEY_PASSWORD = "password"
//        private const val KEY_CONFIRM_PASSWORD = "confirmPassword"
//        private const val PRIVATE_MODE = 0
//        private const val KEY_REGISTERED = "isRegistered"
//    }
//}

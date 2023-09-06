package umbjm.ft.inf.mydigitalprinting.preferen
import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context: Context){

    private val mypref = "main_pref"
    private val sharedPref : SharedPreferences
    val editor : SharedPreferences.Editor

    init {
        sharedPref = context.getSharedPreferences(mypref, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    fun put(key:String, value: String){
        editor.putString(key, value)
    }

    fun getString(key: String) : String?{
        return sharedPref.getString(key, null)
    }

    fun put(key: String , value: Boolean){
        editor.putBoolean(key, value)
            .apply()
    }

    fun getBoolean(key: String) : Boolean{
        return sharedPref.getBoolean(key, false)
    }

    fun clear(){
        editor.clear()
            .apply()
    }

}
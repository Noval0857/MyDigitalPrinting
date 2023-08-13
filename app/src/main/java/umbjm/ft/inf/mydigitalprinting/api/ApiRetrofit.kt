package umbjm.ft.inf.mydigitalprinting.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiRetrofit {

    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    val endpoint:ApiEndPoint
        get() {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.36/MyDigitalPrinting/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(ApiEndPoint::class.java)
        }


}
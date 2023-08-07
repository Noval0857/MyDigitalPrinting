package umbjm.ft.inf.mydigitalprinting.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import umbjm.ft.inf.mydigitalprinting.SubmitModel
import umbjm.ft.inf.mydigitalprinting.model.ResponseLogin

interface ApiEndPoint {

    @FormUrlEncoded
    @POST("register.php")
    fun register(
        @Field("nama") nama: Int,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirm password") confirmpassword: String,
        toString: String,
    ): Call<SubmitModel>

    @FormUrlEncoded
    @POST("Login.php")
    fun Login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<ResponseLogin>
}
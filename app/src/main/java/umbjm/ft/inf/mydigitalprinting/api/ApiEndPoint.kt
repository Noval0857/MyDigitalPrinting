//package umbjm.ft.inf.mydigitalprinting.api
//
//import retrofit2.Call
//import retrofit2.http.Field
//import retrofit2.http.FormUrlEncoded
//import retrofit2.http.GET
//import retrofit2.http.POST
//import retrofit2.http.Path
//import retrofit2.http.Query
//import umbjm.ft.inf.mydigitalprinting.SubmitModel
//import umbjm.ft.inf.mydigitalprinting.model.ResponseLogin
//
//interface ApiEndPoint {
//
//    @FormUrlEncoded
//    @POST("register.php")
//    fun register(
//        @Field("nama") nama: String,
//        @Field("email") email: String,
//        @Field("password") password: String,
//    ): Call<SubmitModel>
//
//    @FormUrlEncoded
//    @POST("login.php")
//    fun login(
//        @Field("email") email: String,
//        @Field("password") password: String,
//    ): Call<ResponseLogin>
//}
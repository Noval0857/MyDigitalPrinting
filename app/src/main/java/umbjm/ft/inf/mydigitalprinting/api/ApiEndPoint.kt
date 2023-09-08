//package umbjm.ft.inf.mydigitalprinting.api
//
//import okhttp3.MultipartBody
//import retrofit2.Call
//import retrofit2.http.Field
//import retrofit2.http.FormUrlEncoded
//import retrofit2.http.GET
//import retrofit2.http.Multipart
//import retrofit2.http.POST
//import retrofit2.http.Part
//import umbjm.ft.inf.mydigitalprinting.SubmitModel
//import umbjm.ft.inf.mydigitalprinting.keranjang.Keranjang
//
//
//interface ApiEndPoint {
//
//    @FormUrlEncoded
//    @POST("register.php")
//    fun register(
//        @Field("nama") nama: String,
//        @Field("email") email: String,
//        @Field("password") password: String,
//        ): Call<SubmitModel>
//
//    @FormUrlEncoded
//    @POST("login.php")
//    fun login(
//        @Field("email") email: String,
//        @Field("password") password: String,
//        ): Call<SubmitModel>
//
//    @Multipart
//    @POST("tambah_sdesain.php")
//    fun tambahSDesain(
//        @Part("jenis_spanduk") jenis_spanduk: String,
//        @Part("teks_utama") teks_utama: String,
//        @Part("teks_lainnya") teks_lainnya: String,
//        @Part("keterangan") keterangan: String,
//        @Part("panjang") panjang: String,
//        @Part("lebar") lebar: String,
//        @Part image: MultipartBody.Part,
//    ): Call<SubmitModel>
//
//    @GET("ViewProduk.php")
//    fun getProduk(): Call<Keranjang>
//}
//
////import retrofit2.Call
////import retrofit2.http.Field
////import retrofit2.http.FormUrlEncoded
////import retrofit2.http.GET
////import retrofit2.http.POST
////import retrofit2.http.Path
////import retrofit2.http.Query
////import umbjm.ft.inf.mydigitalprinting.SubmitModel
////import umbjm.ft.inf.mydigitalprinting.model.ResponseLogin
////
////interface ApiEndPoint {
////
////    @FormUrlEncoded
////    @POST("register.php")
////    fun register(
////        @Field("nama") nama: String,
////        @Field("email") email: String,
////        @Field("password") password: String,
////    ): Call<SubmitModel>
////
////    @FormUrlEncoded
////    @POST("login.php")
////    fun login(
////        @Field("email") email: String,
////        @Field("password") password: String,
////    ): Call<ResponseLogin>
////}
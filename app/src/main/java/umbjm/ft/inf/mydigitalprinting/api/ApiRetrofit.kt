//package umbjm.ft.inf.mydigitalprinting.api
//
//import com.android.volley.BuildConfig
//import com.google.gson.Gson
//import com.google.gson.GsonBuilder
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//class ApiRetrofit {
//
//    private val BASE_URL = "http://192.168.1.14/MyDigitalPrinting/"
//
//    private val okHttpClient = OkHttpClient.Builder()
//        .addInterceptor (HttpLoggingInterceptor().apply {
//            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BODY
//        })
//        .addInterceptor { chain ->
//            val original = chain.request()
//
//            val requestBuilder = original.newBuilder()
//                .method(original.method, original.body)
//
//            val request = requestBuilder.build()
//            chain.proceed(request)
//        }.build()
//
//
//    val endpoint: ApiEndPoint by lazy{
//        val retrofit =Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build()
//
//        retrofit.create(ApiEndPoint::class.java)
//    }
//
//}
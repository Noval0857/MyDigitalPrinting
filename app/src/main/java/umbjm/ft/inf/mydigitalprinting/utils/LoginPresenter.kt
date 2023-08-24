//package umbjm.ft.inf.mydigitalprinting.utils
//
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import umbjm.ft.inf.mydigitalprinting.api.ApiRetrofit
//import umbjm.ft.inf.mydigitalprinting.login.LoginActivity
//import umbjm.ft.inf.mydigitalprinting.model.ResponseLogin
//
//class LoginPresenter(val LoginView: LoginActivity) {
//    fun login(email : String, password : String){
//        ApiRetrofit().endpoint.login(email, password).enqueue(object : Callback<ResponseLogin> {
//            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
//                if (response.isSuccessful && response.body()?.status == 200){
//                    LoginView.onSuccessLogin(response.body()?.message)
//                } else {
//                    LoginView.onFailedLogin(response.body()?.message)
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
//                LoginView.onFailedLogin(t.localizedMessage)
//            }
//
//        })
//    }
//}
package umbjm.ft.inf.mydigitalprinting.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ResponseLogin {
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var user: UserModel? = null

}
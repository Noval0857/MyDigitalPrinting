package umbjm.ft.inf.mydigitalprinting.keranjang

import com.google.gson.annotations.SerializedName

class Keranjang (
    @SerializedName("data") var data : ArrayList<KeranjangItem> = arrayListOf()
)


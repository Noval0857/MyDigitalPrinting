package umbjm.ft.inf.mydigitalprinting.produk.brosur

data class CetakBrosur(
    val idKeranjang: String?= null,
    val userID: String? = null,
    val idBrosur: String? = null,
    val harga: String? = null,
    val namaProject: String?= null,
    val satuSisi: Boolean? = false,
    val duaSisi: Boolean? = false,
    val tanpaLipatan: Boolean? = false,
    val duaLipatan: Boolean? = false,
    val tigaLipatan: Boolean? = false,
    val lipatanZ: Boolean? = false,
    val lipatanGF: Boolean? = false,
    val keterangan: String? = null,
    val hargaSatuan: String? = null,
    val minOrder: String? = null,
    val image: List<String>? = null
)

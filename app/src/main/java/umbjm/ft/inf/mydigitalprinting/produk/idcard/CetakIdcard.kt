package umbjm.ft.inf.mydigitalprinting.produk.idcard

data class CetakIdcard(
    val idKeranjang: String?= null,
    val userID: String? = null,
    val idBanner: String? = null,
    val harga: String? = null,
    val namaProject: String?= null,
    val satuSisi: Boolean? = false,
    val duaSisi: Boolean? = false,
    val B1: Boolean? = false,
    val B2: Boolean? = false,
    val B3: Boolean? = false,
    val B4: Boolean? = false,
    val keterangan: String? = null,
    val hargaSatuan: String? = null,
    val minOrder: String? = null,
    val image: List<String>? = null
)

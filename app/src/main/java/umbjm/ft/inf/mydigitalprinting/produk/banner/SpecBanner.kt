package umbjm.ft.inf.mydigitalprinting.produk.banner

data class SpecBanner(
    val idKeranjang: String?= null,
    val userID: String? = null,
    val idBanner: String? = null,
    val harga: String? = null,
    val namaProject: String?= null,
    val teksUtama: String?= null,
    val teksLainnya: String?= null,
    val keterangan: String? = null,
    val panjang: String? = null,
    val image: List<String>? = null
)


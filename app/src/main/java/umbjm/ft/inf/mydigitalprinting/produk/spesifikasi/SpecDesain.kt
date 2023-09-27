package umbjm.ft.inf.mydigitalprinting.produk.spesifikasi

data class SpecDesain(
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


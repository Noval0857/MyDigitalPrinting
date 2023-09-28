package umbjm.ft.inf.mydigitalprinting.produk.cetak

data class CetakDesain(
    val idKeranjang: String?= null,
    val userID: String? = null,
    val idBanner: String? = null,
    val harga: String? = null,
    val namaProject: String?= null,
    val wa: String?= null,
    val keterangan: String? = null,
    val panjang: String? = null,
    val lebar: String? = null,
    val image: List<String>? = null
)

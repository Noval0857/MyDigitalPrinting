package umbjm.ft.inf.mydigitalprinting.produk.stiker

data class SpecSticker(
    val idPesanan: String? = null,
    val userID: String? = null,
    val idSticker: String? = null,
    val harga: String? = null,
    val namaProject: String? = null,
    val namamerk: String? = null,
    val keterangan: String? = null,
    val panjang: String? = null,
    val lebar: String? = null,
    val image: String? = "",
    val kotak: Boolean? = false,
    val oval: Boolean? = false,
    val bulat: Boolean? = false,
    val costume: Boolean? = false
)

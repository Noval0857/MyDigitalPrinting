package umbjm.ft.inf.mydigitalprinting.produk.brosur

data class SpecBrosur(
    val idPesanan: String?= null,
    val userID: String? = null,
    val idBrosur: String? = null,
    val harga: String? = null,
    val namaProject: String?= null,
    val satuSisi: Boolean? = false,
    val duaSisi: Boolean? = false,
    val keterangan: String? = null,
    val sisiBelakang: String? = null,
    val keteranganTambahan: String? = null,
    val image: String? = ""
)

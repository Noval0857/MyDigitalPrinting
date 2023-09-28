package umbjm.ft.inf.mydigitalprinting.pesanan

data class DataPesanan(
    val idPesanan: String? = null,
    val idPembayaran: String? = null,
    val idKeranjang: String? = null,
    val userID: String? = null,
    val ttotalHarga: String? = null,
    val imageBukti: String? = ""
)

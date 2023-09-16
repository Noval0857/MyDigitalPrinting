package umbjm.ft.inf.mydigitalprinting.produk.undangan

data class SpecUndangan(
    val idPesanan: String? = null,
    val userID: String? = null,
    val idUndangan: String? = null,
    val harga: String? = null,
    val namaProject: String? = null,
    val jenisUndangan: String? = null,
    val costume: String? = null,
    val keteranganlainnya: String? = null,
    val ukuranA4: Boolean? = false,
    val ukuranA5: Boolean? = false,
    val costumeBox: Boolean? = false,
    val satusisi: Boolean? = false,
    val duasisi: Boolean? = false,
    val image: String? = ""
)
package umbjm.ft.inf.mydigitalprinting.produk.sertifikat

data class SpecSertifikat(
    val idPesanan: String?= null,
    val userID: String? = null,
    val idSertifikat: String? = null,
    val harga: String? = null,
    val namaProject: String?= null,
    val costume: String?= null,
    val landscape: Boolean? = false,
    val potrait: Boolean? = false,
    val ukuranA4: Boolean? = false,
    val ukuranF5: Boolean? = false,
    val sertifikat: Boolean? = false,
    val penghargaan: Boolean? = false,
    val piagam: Boolean? = false,
    val costum: Boolean? = false,
    val keterangan: String? = null,
    val imageDesain: String? = "",
    val dataPeserta: String? = ""

)
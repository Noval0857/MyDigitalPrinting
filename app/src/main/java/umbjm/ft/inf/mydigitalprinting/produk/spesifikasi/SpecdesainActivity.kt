package umbjm.ft.inf.mydigitalprinting.produk.spesifikasi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.databinding.ActivitySpesifikasidesainBinding


class SpecdesainActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpesifikasidesainBinding
    private lateinit var database: DatabaseReference
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpesifikasidesainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idBanner = intent.getStringExtra("idBanner")
        val loginstatus = intent.getStringExtra("login_status")
        if (idBanner != null && idBanner.isNotEmpty()) {
            // Lanjutkan dengan penggunaan idBanner
            imageInput()
            upload()
        } else {
            // Handle jika idBanner null atau kosong
            Toast.makeText(this, "ID Banner tidak valid", Toast.LENGTH_SHORT).show()
            finish() // Sebaiknya kembali ke aktivitas sebelumnya atau tutup aktivitas ini jika ID tidak valid
        }




    }

    private fun imageInput() {
        binding.image.setOnClickListener {
            resultLauncher.launch("image/*")
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {

        imageUri = it
        binding.image.setImageURI(it)
    }


    private fun upload() {
        binding.btnS1.setOnClickListener {
            val jenis = binding.UploadJenis.text.toString()
            val teksUtama = binding.UploadteksUtama.text.toString()
            val teksLainnya = binding.UploadteksLainnya.text.toString()
            val keterangan = binding.Uploadketerangan.text.toString()
            val panjang = binding.Uploadpanjang.text.toString()
            val lebar = binding.Uploadlebar.text.toString()


            // Referensi Firebase Storage
            val storageRef = FirebaseStorage.getInstance().reference.child("Pesanan")
                .child(System.currentTimeMillis().toString())

            // Mengunggah gambar ke Firebase Storage
            imageUri?.let {
                storageRef.putFile(it).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Jika pengunggahan berhasil, dapatkan URL gambar
                        storageRef.downloadUrl.addOnSuccessListener { uri ->
                            // Simpan URL gambar ke Firebase Realtime Database
                            val imageUrl = uri.toString()
                            database =
                                FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                    .getReference("User")
                            val idProduk = database.push().key!!
                            val sD = SpecDesain(
                                idProduk,
                                jenis,
                                teksUtama,
                                teksLainnya,
                                keterangan,
                                panjang,
                                lebar,
                                imageUrl
                            )
                            database.child("Pesanan").setValue(sD)
                                .addOnCompleteListener { databaseTask ->
                                    if (databaseTask.isSuccessful) {
                                        Toast.makeText(
                                            this,
                                            "Uploaded Successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                                    }
                                    binding.UploadJenis.text?.clear()
                                    binding.UploadteksUtama.text?.clear()
                                    binding.UploadteksLainnya.text?.clear()
                                    binding.Uploadketerangan.text?.clear()
                                    binding.Uploadpanjang.text?.clear()
                                    binding.Uploadlebar.text?.clear()

                                }
                        }
                    } else {
                        Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

//    private val ActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
//        ActivityResultContracts.StartActivityForResult()
//    ){result: ActivityResult ->
//        if (result.resultCode== RESULT_OK){
//            val uri = result.data!!.data
//            try {
//                val inputStream = contentResolver.openInputStream(uri!!)
//                val myBitmap = BitmapFactory.decodeStream(inputStream)
//                val stream = ByteArrayOutputStream()
//                myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//                val bytes = stream.toByteArray()
//
//                binding.image.setImageBitmap(myBitmap)
//                inputStream!!.close()
//            } catch (ex: Exception){
//                Toast.makeText(this, ex.message.toString(), Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

//class SpecdesainActivity : AppCompatActivity() {
//
//    private lateinit var bayar: MaterialButton
//    private lateinit var jenis_spanduk: TextInputEditText
//    private lateinit var teks_utama: TextInputEditText
//    private lateinit var teks_lainnya: TextInputEditText
//    private lateinit var keterangan: TextInputEditText
//    private lateinit var panjang: TextInputEditText
//    private lateinit var lebar: TextInputEditText
//    private lateinit var image: ImageView
//
//    //    private var selectedImageUri: Uri? = null
//    private lateinit var database: DatabaseReference
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_spesifikasidesain)
//
//        bayar = findViewById(R.id.btnS1)
//        jenis_spanduk = findViewById(R.id.jenis)
//        teks_utama = findViewById(R.id.teks_utama)
//        teks_lainnya = findViewById(R.id.teks_lainnya)
//        keterangan = findViewById(R.id.keterangan)
//        panjang = findViewById(R.id.panjang)
//        lebar = findViewById(R.id.lebar)
//        image = findViewById(R.id.image)
//
//        database = FirebaseDatabase.getInstance().getReference("SpecDesain")
//
//        bayar.setOnClickListener {
//            Pesan()
//        }
//    }
//
//    private fun Pesan() {
//        val jenisSpanduk = jenis_spanduk.text.toString()
//        val teksUtama = teks_utama.text.toString()
//        val teksLainnya = teks_lainnya.text.toString()
//        val Keterangan = keterangan.text.toString()
//        val Panjang = panjang.text.toString()
//        val Lebar = lebar.text.toString()
//
//
//        val sdesainId = database.push().key.toString()
//        val SDesain = specDesain(jenisSpanduk, teksUtama, teksLainnya, Keterangan, Panjang, Lebar)
//        database.child(sdesainId).setValue(SDesain).addOnCompleteListener {
//            jenis_spanduk.setText("")
//            teks_utama.setText("")
//            teks_lainnya.setText("")
//            keterangan.setText("")
//            panjang.setText("")
//            lebar.setText("")
//            Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }.addOnFailureListener {
//            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
//        }
//    }
//}
//    private fun openFile() {
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "image/*"
//        startActivityForResult(intent, PICK_IMAGE_REQUEST)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
//            selectedImageUri = data.data
//            image.setImageURI(selectedImageUri)
//        }
//    }
//
//    companion object {
//        private const val PICK_IMAGE_REQUEST = 1
//    }
//
//    private fun tambahSDesain() {
//        val jenisSpanduk = jenis_spanduk.text.toString()
//        val teksUtama = teks_utama.text.toString()
//        val teksLainnya = teks_lainnya.text.toString()
//        val keterangan = keterangan.text.toString()
//        val panjang = panjang.text.toString()
//        val lebar = lebar.text.toString()
//
//        // Validasi apakah selectedImageUri tidak null
//        if (selectedImageUri == null) {
//            Toast.makeText(this, "Pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        // Mengambil nama file dari URI
//        val fileName = getFileName(selectedImageUri!!)
//
//        // Membaca data gambar ke dalam byteArray
//        val byteArray = contentResolver.openInputStream(selectedImageUri!!)?.readBytes()
//
//        // Membuat request body dari byteArray
//        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), byteArray!!)
//
//        // Membuat MultipartBody.Part
//        val image = MultipartBody.Part.createFormData("image", fileName, requestFile)
//
//        ApiRetrofit().endpoint.tambahSDesain(
//            jenisSpanduk,
//            teksUtama,
//            teksLainnya,
//            keterangan,
//            panjang,
//            lebar,
//            image
//        ).enqueue(object : Callback<SubmitModel> {
//            override fun onResponse(
//                call: Call<SubmitModel>,
//                response: Response<SubmitModel>
//            ) {
//                if (response.isSuccessful) {
//                    // Tanggapan sukses, lakukan sesuatu jika diperlukan
//                } else {
//                    // Tanggapan gagal, tangani kesalahan jika diperlukan
//                }
//            }
//
//            override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
//                // Permintaan gagal, tangani kesalahan jika diperlukan
//            }
//        })
//
//        val intent = Intent(this, PembayaranActivity::class.java)
//        startActivity(intent)
//        finish()
//    }
//
//    @SuppressLint("Range")
//    private fun getFileName(uri: Uri): String {
//        var result: String? = null
//        if (uri.scheme == "content") {
//            val cursor = contentResolver.query(uri, null, null, null, null)
//            try {
//                if (cursor != null && cursor.moveToFirst()) {
//                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
//                }
//            } finally {
//                cursor?.close()
//            }
//        }
//        if (result == null) {
//            result = uri.path
//            val cut = result!!.lastIndexOf('/')
//            if (cut != -1) {
//                result = result.substring(cut + 1)
//            }
//        }
//        return result
//    }

package umbjm.ft.inf.mydigitalprinting.produk.idcard

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.databinding.ActivitySpesifikasiidcardBinding
import umbjm.ft.inf.mydigitalprinting.produk.spesifikasi.SpecDesain

class SpecidcardActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpesifikasiidcardBinding
    private lateinit var database: DatabaseReference
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpesifikasiidcardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idCard = intent.getStringExtra("idCard")
        val loginstatus = intent.getStringExtra("login_status")
        if (idCard != null && idCard.isNotEmpty()) {
            // Lanjutkan dengan penggunaan idBanner
            imageInput()
            upload()
        } else {
            // Handle jika idBanner null atau kosong
            Toast.makeText(this, "ID Card tidak valid", Toast.LENGTH_SHORT).show()
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
        binding.btnPesan.setOnClickListener {
            val namaProject = binding.namaProject.text.toString()
            val satuSisi = binding.satusisi.text.toString()
            val duaSisi = binding.duasisi.text.toString()
            val keterangan = binding.keterangan.text.toString()
            val sisiBelakang = binding.sisibelakang.text.toString()
            val keteranganTambahan = binding.keteranganTambahan.text.toString()


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
                            val specIdcard = SpecIdcard(idProduk, namaProject, satuSisi, duaSisi, keterangan, sisiBelakang, keteranganTambahan, imageUrl)
                            database.child(idProduk).setValue(specIdcard)
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
                                    binding.namaProject.text?.clear()
                                    binding.satusisi.isChecked = false
                                    binding.duasisi.isChecked = false
                                    binding.keterangan.text?.clear()
                                    binding.sisibelakang.text?.clear()
                                    binding.keteranganTambahan.text?.clear()

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
package umbjm.ft.inf.mydigitalprinting.produk.idcard

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.databinding.ActivitySpesifikasiidcardBinding

class SpecidcardActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpesifikasiidcardBinding
    private lateinit var database: DatabaseReference
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpesifikasiidcardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageInput()
        upload()


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

            val user = FirebaseAuth.getInstance().currentUser
            val userID = user?.uid

            val idCard = intent.getStringExtra("idCard")
            val harga = intent.getStringExtra("harga")

            val namaProject = binding.UploadNamaProject.text.toString()
            val satuSisiCheckbox = findViewById<CheckBox>(R.id.satusisiCheckbox)
            val duaSisiCheckbox = findViewById<CheckBox>(R.id.duasisiCheckbox)
            val satuSisi = satuSisiCheckbox.isChecked
            val duaSisi = duaSisiCheckbox.isChecked
            val keterangan = binding.Uploadketerangan.text.toString()
            val sisiBelakang = binding.UploadSisiBelakang.text.toString()
            val keteranganTambahan = binding.UploadketeranganTambahan.text.toString()


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
                            val idPesanan = database.push().key!!
                            val specIdcard = SpecIdcard(idPesanan, userID, idCard, harga, namaProject, satuSisi, duaSisi, keterangan, sisiBelakang, keteranganTambahan, imageUrl)
                            database.child(userID!!).child("Pesanan").child(idPesanan).setValue(specIdcard)
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
                                    binding.UploadNamaProject.text?.clear()
                                    satuSisiCheckbox.isChecked = false
                                    duaSisiCheckbox.isChecked = false
                                    binding.Uploadketerangan.text?.clear()
                                    binding.UploadSisiBelakang.text?.clear()
                                    binding.UploadketeranganTambahan.text?.clear()

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
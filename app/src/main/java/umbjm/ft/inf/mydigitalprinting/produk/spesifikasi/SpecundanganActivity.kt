package umbjm.ft.inf.mydigitalprinting.produk.spesifikasi

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.FirebaseStorage
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.databinding.ActivitySpesifikasiundanganBinding
import umbjm.ft.inf.mydigitalprinting.produk.undangan.SpecUndangan

class SpecundanganActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpesifikasiundanganBinding
    private lateinit var database: DatabaseReference
    private var imageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySpesifikasiundanganBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
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
            val userID = user?.uid // Mendapatkan ID pengguna saat ini

            val idUndangan = intent.getStringExtra("idUndangan")
            val harga = intent.getStringExtra("harga")

            // Isi dengan text
            val namaProject = binding.namaProject.text.toString()
            val keterangan = binding.Keteranganlainnya.text.toString()
            val jenisUndangan = binding.jenisUndangan.text.toString()
            val costum = binding.costum.text.toString()

            // Isi dengan Checkbox Muka dan belakang
            val satusisiCheckBox = findViewById<CheckBox>(R.id.satusisiCheckbox)
            val duasisiCheckBox = findViewById<CheckBox>(R.id.duasisiCheckbox)

            // Mengambil isian Checkbox
            val satusisi = satusisiCheckBox.isChecked
            val duasisi = duasisiCheckBox.isChecked

            // Isi ukuran Checkbox
            val uA4 = findViewById<CheckBox>(R.id.ukuranA4)
            val uA5 = findViewById<CheckBox>(R.id.ukuranA5)
            val uCos = findViewById<CheckBox>(R.id.costumeCheckbox)

            // Mengambil isian Checkbox ukuran
            val ukuranA4 = uA4.isChecked
            val ukuranA5 = uA5.isChecked
            val costumBox = uCos.isChecked


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
                                FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")
                            val idPesanan = database.push().key!!
                            val sD = SpecUndangan(
                                idPesanan,
                                userID,
                                idUndangan,
                                harga,
                                namaProject,
                                jenisUndangan,
                                costum,
                                keterangan,
                                satusisi,
                                duasisi,
                                ukuranA4,
                                ukuranA5,
                                costumBox,
                                imageUrl
                            )
                            database.child(userID!!).child("Pesanan").child(idPesanan).setValue(sD)
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
                                    binding.jenisUndangan.text?.clear()
                                    binding.Keteranganlainnya.text?.clear()
                                    uA4.isChecked = false
                                    uA5.isChecked = false
                                    uCos.isChecked = false
                                    satusisiCheckBox.isChecked = false
                                    duasisiCheckBox.isChecked = false
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
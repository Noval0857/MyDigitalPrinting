package umbjm.ft.inf.mydigitalprinting.admin.itemsertifikat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import umbjm.ft.inf.mydigitalprinting.admin.AdminActivity
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityItemSertifikatBinding

class ItemSertifikatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemSertifikatBinding
    private lateinit var database: DatabaseReference
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemSertifikatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageInput()
        upload()
        showP()
    }

    private fun showP() {
        binding.showP.setOnClickListener {
            startActivity(Intent(this, ItemSertifikatProduk::class.java))
        }
    }


    private fun imageInput() {
        binding.imageSertifikat.setOnClickListener {
            resultLauncher.launch("image/*")
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {

        imageUri = it
        binding.imageSertifikat.setImageURI(it)
    }

    private fun upload() {
        binding.upload.setOnClickListener {
            val nameSertifikat = binding.nameSertifikat.text.toString()
            val hargaSertifikat = binding.hargaSertifikat.text.toString()


            val storageRef = FirebaseStorage.getInstance().reference.child("Products").child(System.currentTimeMillis().toString())

            imageUri?.let {
                storageRef.putFile(it).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Jika pengunggahan berhasil, dapatkan URL gambar
                        storageRef.downloadUrl.addOnSuccessListener { uri ->
                            // Simpan URL gambar ke Firebase Realtime Database
                            val imageUrl = uri.toString()
                            database = FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Products")
                            val idSertifikat = database.push().key!!
                            val products = SertifikatItem(idSertifikat, nameSertifikat, hargaSertifikat, imageUrl)

                            // Menambahkan item banner sebagai child dari id produk
                            database.child("Sertifikat").child(idSertifikat).setValue(products).addOnCompleteListener { databaseTask ->
                                if (databaseTask.isSuccessful) {
                                    Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, AdminActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                                }
                                binding.nameSertifikat.text?.clear()
                                binding.hargaSertifikat.text?.clear()
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
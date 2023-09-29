package umbjm.ft.inf.mydigitalprinting.admin.itemidcard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import umbjm.ft.inf.mydigitalprinting.admin.AdminActivity
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityItemIdcardBinding

class ItemIdcardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemIdcardBinding
    private lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemIdcardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageInput()
        upload()
        showP()
    }

    private fun showP() {
        binding.showP.setOnClickListener {
            startActivity(Intent(this, ItemIdcardProduk::class.java))
        }
    }


    private fun imageInput() {
        binding.imageIdcard.setOnClickListener {
            resultLauncher.launch("image/*")
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {

        imageUri = it
        binding.imageIdcard.setImageURI(it)
    }

    private fun upload() {
        binding.upload.setOnClickListener {
            val nameIdcard = binding.nameIdcard.text.toString()
            val hargaIdcard = binding.hargaIdcard.text.toString()


            val storageRef = FirebaseStorage.getInstance().reference.child("Products/Idcard").child(System.currentTimeMillis().toString())

            imageUri?.let {
                storageRef.putFile(it).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Jika pengunggahan berhasil, dapatkan URL gambar
                        storageRef.downloadUrl.addOnSuccessListener { uri ->
                            // Simpan URL gambar ke Firebase Realtime Database
                            val imageUrl = uri.toString()
                            database = FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Products")
                            val idCard = database.push().key!!
                            val products = IdcardItem(idCard, nameIdcard, hargaIdcard, imageUrl)

                            // Menambahkan item banner sebagai child dari id produk
                            database.child("Idcard").child(idCard).setValue(products).addOnCompleteListener { databaseTask ->
                                if (databaseTask.isSuccessful) {
                                    Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, AdminActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                                }
                                binding.nameIdcard.text?.clear()
                                binding.hargaIdcard.text?.clear()
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
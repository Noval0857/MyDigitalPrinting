package umbjm.ft.inf.mydigitalprinting.admin.itembanner

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
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityItemBannerBinding

class ItemBannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemBannerBinding
    private lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth
    private var imageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemBannerBinding.inflate(layoutInflater)
        setContentView(binding.root)



        imageInput()
        upload()
        show()

    }

    private fun show(){
        binding.show.setOnClickListener {
            startActivity(Intent(this, ItemBannerProduk::class.java))
        }
    }


    private fun imageInput() {
        binding.imageBanner.setOnClickListener {
            resultLauncher.launch("image/*")
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {

        imageUri = it
        binding.imageBanner.setImageURI(it)
    }

    private fun upload() {
        binding.upload.setOnClickListener {
            val nameBanner = binding.nameBanner.text.toString()
            val hargaBanner = binding.hargaBanner.text.toString()


            val storageRef = FirebaseStorage.getInstance().reference.child("Products/Banner").child(System.currentTimeMillis().toString())

            imageUri?.let {
                storageRef.putFile(it).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Jika pengunggahan berhasil, dapatkan URL gambar
                        storageRef.downloadUrl.addOnSuccessListener { uri ->
                            // Simpan URL gambar ke Firebase Realtime Database
                            val imageUrl = uri.toString()
                            database = FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Products")
                            val idBanner = database.push().key!!
                            val products = BannerItem(idBanner, nameBanner, hargaBanner, imageUrl)

                            // Menambahkan item banner sebagai child dari id produk
                            database.child("Banner").child(idBanner).setValue(products).addOnCompleteListener { databaseTask ->
                                if (databaseTask.isSuccessful) {
                                    Toast.makeText(this, "Uploaded Successfully", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, AdminActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                                }
                                binding.nameBanner.text?.clear()
                                binding.hargaBanner.text?.clear()
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

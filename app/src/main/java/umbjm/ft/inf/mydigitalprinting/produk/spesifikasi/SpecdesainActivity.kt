package umbjm.ft.inf.mydigitalprinting.produk.spesifikasi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.databinding.ActivitySpesifikasidesainBinding
import umbjm.ft.inf.mydigitalprinting.produk.banner.BannerActivity

class SpecdesainActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpesifikasidesainBinding
    private lateinit var database: DatabaseReference
    private var uris: List<Uri>? = null
    private lateinit var BackPesanan: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpesifikasidesainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // back mainActivity
        BackPesanan = findViewById(R.id.BackPesanan)
        BackPesanan.setOnClickListener {
            val intent = Intent(this, BannerActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }

        imageInput()
        upload()
    }

    private fun imageInput() {
        binding.image.setOnClickListener {
            resultLauncher.launch("image/*")
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        // Handle the selected image URIs here (uris is a list of URIs)
        this.uris = uris
    }

    private fun upload() {
        binding.btnS1.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            val userID = user?.uid

            val idBanner = intent.getStringExtra("idBanner")
            val harga = intent.getStringExtra("biayaDesain")

            val namaProject = binding.UploadJenis.text.toString()
            val teksUtama = binding.UploadteksUtama.text.toString()
            val teksLainnya = binding.UploadteksLainnya.text.toString()
            val keterangan = binding.Uploadketerangan.text.toString()
            val panjang = binding.Uploadpanjang.text.toString()

            if (uris.isNullOrEmpty()) {
                Toast.makeText(this, "Select one or more images", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Initialize Firebase references
            val storage = FirebaseStorage.getInstance()
            database = FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")

            // Loop through each selected image URI and upload it
            val idKeranjang = database.push().key!!
            val imagesUploaded = mutableListOf<String>()

            for (imageUri in uris!!) {
                val storageRef = storage.reference.child("Keranjang")
                    .child(System.currentTimeMillis().toString())

                // Upload the image to Firebase Storage
                storageRef.putFile(imageUri).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // If the upload is successful, get the image URL
                        storageRef.downloadUrl.addOnSuccessListener { uri ->
                            val imageUrl = uri.toString()
                            imagesUploaded.add(imageUrl)

                            // Check if all images are uploaded
                            if (imagesUploaded.size == uris!!.size) {
                                // Save the image URLs in Firebase Realtime Database
                                val sD = SpecDesain(
                                    idKeranjang,
                                    userID,
                                    idBanner,
                                    harga,
                                    namaProject,
                                    teksUtama,
                                    teksLainnya,
                                    keterangan,
                                    panjang,
                                    imagesUploaded
                                )

                                database.child(userID!!).child("Keranjang").child(idKeranjang)
                                    .setValue(sD)
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
                                            Toast.makeText(this, "Failed to upload data", Toast.LENGTH_SHORT).show()
                                        }

                                        // Clear input fields
                                        binding.UploadJenis.text?.clear()
                                        binding.UploadteksUtama.text?.clear()
                                        binding.UploadteksLainnya.text?.clear()
                                        binding.Uploadketerangan.text?.clear()
                                        binding.Uploadpanjang.text?.clear()
                                    }
                            }
                        }
                    } else {
                        Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

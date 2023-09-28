package umbjm.ft.inf.mydigitalprinting.produk.cetak

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityCetakBinding
import umbjm.ft.inf.mydigitalprinting.databinding.ActivitySpesifikasidesainBinding
import umbjm.ft.inf.mydigitalprinting.produk.opsi.OpsipesananActivity
import umbjm.ft.inf.mydigitalprinting.produk.spesifikasi.SpecDesain

class CetakdesainActivity : AppCompatActivity() {

    private lateinit var pesan:MaterialButton
    private lateinit var btnKembali:MaterialButton
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityCetakBinding
    private var uris: List<Uri>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCetakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnKembali = findViewById(R.id.btnKembali)
        btnKembali.setOnClickListener {
            val intent = Intent(this, OpsipesananActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }

        pesan = findViewById(R.id.btnPesan)


        imageInput()
        upload()

    }

    private fun upload() {
        pesan.setOnClickListener {

            val user = FirebaseAuth.getInstance().currentUser
            val userID = user?.uid

            val idBanner = intent.getStringExtra("idBanner")
            val harga = intent.getStringExtra("harga")

            val namaProject = binding.UploadNamaProject.text.toString()
            val wa = binding.whatsapp.text.toString()
            val panjang = binding.panjang.text.toString()
            val lebar = binding.lebar.text.toString()
            val keterangan = binding.UploadketeranganTambahan.text.toString()

            if (uris.isNullOrEmpty()) {
                Toast.makeText(this, "Select one or more images", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val storage = FirebaseStorage.getInstance()
            database = FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")

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
                                val cetak = CetakDesain(
                                    idKeranjang,
                                    userID,
                                    idBanner,
                                    harga,
                                    namaProject,
                                    wa,
                                    keterangan,
                                    panjang,
                                    lebar,
                                    imagesUploaded
                                )

                                database.child(userID!!).child("Keranjang").child(idKeranjang)
                                    .setValue(cetak)
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
                                        binding.UploadNamaProject.text?.clear()
                                        binding.whatsapp.text?.clear()
                                        binding.panjang.text?.clear()
                                        binding.lebar.text?.clear()
                                        binding.UploadketeranganTambahan.text?.clear()
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
}
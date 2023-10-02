package umbjm.ft.inf.mydigitalprinting.produk.idcard

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
import com.google.firebase.storage.FirebaseStorage
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityCetakidcardBinding
import umbjm.ft.inf.mydigitalprinting.produk.opsi.OpsiidcardActivity

class CetakidcardActivity : AppCompatActivity() {

    private lateinit var bayar:MaterialButton
    private lateinit var btnKembali:MaterialButton
    private lateinit var binding: ActivityCetakidcardBinding
    private lateinit var database: DatabaseReference
    private var uris: List<Uri>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCetakidcardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnKembali = findViewById(R.id.btnKembali)
        btnKembali.setOnClickListener {
            val intent = Intent(this, OpsiidcardActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }

        bayar = findViewById(R.id.btnPesan)

        imageInput()
        upload()
    }

    private fun upload() {
        binding.btnPesan.setOnClickListener {

            val user = FirebaseAuth.getInstance().currentUser
            val userID = user?.uid

            val idBanner = intent.getStringExtra("idBanner")

            val namaProject = binding.UploadNamaProject.text.toString()

            val satuSisiCheckbox = findViewById<CheckBox>(R.id.satusisi)
            val duaSisiCheckbox = findViewById<CheckBox>(R.id.duasisi)
            val satuSisi = satuSisiCheckbox.isChecked
            val duaSisi = duaSisiCheckbox.isChecked

            val ukuranB1 = findViewById<CheckBox>(R.id.B1)
            val ukuranB2 = findViewById<CheckBox>(R.id.B2)
            val ukuranB3 = findViewById<CheckBox>(R.id.B3)
            val ukuranB4 = findViewById<CheckBox>(R.id.B4)
            val B1 = ukuranB1.isChecked
            val B2 = ukuranB2.isChecked
            val B3 = ukuranB3.isChecked
            val B4 = ukuranB4.isChecked

            val hargaSatuan = 1000
            val minOrder = binding.minOrder.text.toString().toIntOrNull() ?: 0

            val harga = (hargaSatuan * minOrder).toString()

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
                                val cetak = CetakIdcard(
                                    idKeranjang,
                                    userID,
                                    idBanner,
                                    harga,
                                    namaProject,
                                    satuSisi,
                                    duaSisi,
                                    B1, B2, B3, B4,
                                    hargaSatuan.toString(),
                                    keterangan, minOrder.toString(),
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
                                        satuSisiCheckbox.isChecked = false
                                        duaSisiCheckbox.isChecked = false
                                        ukuranB1.isChecked = false
                                        ukuranB2.isChecked = false
                                        ukuranB3.isChecked = false
                                        ukuranB4.isChecked = false
                                        binding.minOrder.text?.clear()
                                        binding.hargaSatuan.text?.clear()
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
package umbjm.ft.inf.mydigitalprinting.produk.brosur

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityCetakbrosurBinding

class CetakbrosurActivity : AppCompatActivity() {

    private lateinit var btnKembali:MaterialButton
    private lateinit var binding: ActivityCetakbrosurBinding
    private lateinit var database: DatabaseReference
    private var uris: List<Uri>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCetakbrosurBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnKembali = findViewById(R.id.btnKembali)
        btnKembali.setOnClickListener {
            val intent = Intent(this, OpsibrosurActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }

        imageInput()
        upload()
    }

    private fun upload() {
        binding.btnPesan.setOnClickListener {

            val user = FirebaseAuth.getInstance().currentUser
            val userID = user?.uid

            val idBrosur = intent.getStringExtra("idBrosur")

            val namaProject = binding.UploadNamaProject.text.toString()

            val satuSisiCheckbox = findViewById<CheckBox>(R.id.satusisi)
            val duaSisiCheckbox = findViewById<CheckBox>(R.id.duasisi)
            val satuSisi = satuSisiCheckbox.isChecked
            val duaSisi = duaSisiCheckbox.isChecked

            val tanpaLipatan = findViewById<CheckBox>(R.id.tanpaLipat)
            val duaLipatan = findViewById<CheckBox>(R.id.duaLipatan)
            val tigaLipatan = findViewById<CheckBox>(R.id.tigaLipatan)
            val lipatanZ = findViewById<CheckBox>(R.id.lipatanZ)
            val lipatanGF = findViewById<CheckBox>(R.id.lipatanGateFold)
            val TanpaLipatan = tanpaLipatan.isChecked
            val DuaLipatan = duaLipatan.isChecked
            val TigaLipatan = tigaLipatan.isChecked
            val LipatanZ = lipatanZ.isChecked
            val LipatanGF = lipatanGF.isChecked

            val hargaSatuan = 2000
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
                                val cetak = CetakBrosur(
                                    idKeranjang,
                                    userID,
                                    idBrosur,
                                    harga,
                                    namaProject,
                                    satuSisi,
                                    duaSisi,
                                    TanpaLipatan, DuaLipatan, TigaLipatan, LipatanZ, LipatanGF,
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
                                        tanpaLipatan.isChecked = false
                                        duaLipatan.isChecked = false
                                        tigaLipatan.isChecked = false
                                        lipatanZ.isChecked = false
                                        lipatanGF.isChecked = false
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
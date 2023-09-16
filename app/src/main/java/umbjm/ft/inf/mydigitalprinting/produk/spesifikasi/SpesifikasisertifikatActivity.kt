package umbjm.ft.inf.mydigitalprinting.produk.spesifikasi

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
import umbjm.ft.inf.mydigitalprinting.databinding.ActivitySpesifikasisertifikatBinding
import umbjm.ft.inf.mydigitalprinting.produk.sertifikat.SpecSertifikat

class SpesifikasisertifikatActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpesifikasisertifikatBinding
    private lateinit var database: DatabaseReference
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySpesifikasisertifikatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        imageInput()
        upload()


    }

    private fun imageInput() {
        binding.imageDesain.setOnClickListener {
            resultLauncher.launch("image/*")
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        imageUri = it
        binding.imageDesain.setImageURI(it)
    }

    private fun upload() {
        binding.btnPesan.setOnClickListener {

            val user = FirebaseAuth.getInstance().currentUser
            val userID = user?.uid

            val idSertifikat = intent.getStringExtra("idSertifikat")
            val harga = intent.getStringExtra("harga")

            val namaProject = binding.namaProject.text.toString()
            val keterangan = binding.Uploadketerangan.text.toString()
            val costume = binding.costume.text.toString()

            // Checkbox Ukuran
            val uA4 = findViewById<CheckBox>(R.id.ukuranA4)
            val uF5 = findViewById<CheckBox>(R.id.ukuranF5)

            // Verifikasi Checkbox Ukuran
            val ukuranA4 = uA4.isChecked
            val ukuranF5 = uF5.isChecked

            // Checkbox Bentuk
            val pot = findViewById<CheckBox>(R.id.potrait)
            val land = findViewById<CheckBox>(R.id.landscape)

            // Verifikasi Checkbox Bentuk
            val potrait = pot.isChecked
            val landscape = land.isChecked

            // Checkbox Jenis
            val ser = findViewById<CheckBox>(R.id.sertifikat)
            val hargaan = findViewById<CheckBox>(R.id.penghargaan)
            val gam = findViewById<CheckBox>(R.id.piagam)
            val cos = findViewById<CheckBox>(R.id.costumeCheckbox)

            // Verifikasi Checkbox jenis
            val sertifikat = ser.isChecked
            val penghargaan = hargaan.isChecked
            val piagam = gam.isChecked
            val costum = cos.isChecked



            val storageRef = FirebaseStorage.getInstance().reference.child("Pesanan")
                .child(System.currentTimeMillis().toString())

            imageUri?.let {
                storageRef.putFile(it).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        storageRef.downloadUrl.addOnCompleteListener { uri ->
                            val imageUrl = uri.toString()
                            database =
                                FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                    .getReference("User")

                            val idPesanan = database.push().key!!
                            val specsertifikat = SpecSertifikat(
                                idPesanan,
                                userID,
                                idSertifikat,
                                harga,
                                namaProject,
                                costume,
                                potrait,
                                landscape,
                                ukuranA4,
                                ukuranF5,
                                sertifikat,
                                penghargaan,
                                piagam,
                                costum,
                                keterangan,
                                imageUrl
                            )
                            database.child(userID!!).child("Pesanan").child(idPesanan)
                                .setValue(specsertifikat)
                                .addOnCompleteListener { databaseTask ->
                                    if (databaseTask.isSuccessful) {
                                        Toast.makeText(
                                            this,
                                            "Upload Successfully",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(this, "Gagal Upload", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                    binding.namaProject.text?.clear()
                                    uA4.isChecked = false
                                    uF5.isChecked = false
                                    pot.isChecked = false
                                    land.isChecked = false
                                    ser.isChecked = false
                                    hargaan.isChecked = false
                                    gam.isChecked = false
                                    cos.isChecked = false
                                    binding.costume.text?.toString()
                                    binding.Uploadketerangan.text?.toString()
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
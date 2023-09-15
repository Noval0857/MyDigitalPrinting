package umbjm.ft.inf.mydigitalprinting.produk.spesifikasi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.databinding.ActivitySpesifikasistikerBinding
import umbjm.ft.inf.mydigitalprinting.produk.stiker.SpecSticker

class SpecstickerActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpesifikasistikerBinding
    private lateinit var database: DatabaseReference
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySpesifikasistikerBinding.inflate(layoutInflater)
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
            val userID = user?.uid

            val idSticker = intent.getStringExtra("idSticker")
            val harga = intent.getStringExtra("harga")

            val namaProject = binding.namaProject.text.toString()
            val namamerk = binding.namaMerk.text.toString()
            val keterangan = binding.Uploadketerangan.text.toString()
            val panjang = binding.panjang.text.toString()
            val lebar = binding.lebar.text.toString()

            val kotakCheckbox = findViewById<CheckBox>(R.id.kotakCheckbox)
            val ovalCheckbox = findViewById<CheckBox>(R.id.ovalCheckbox)
            val bulatCheckbox = findViewById<CheckBox>(R.id.bulatCheckbox)
            val costumeCheckbox = findViewById<CheckBox>(R.id.costumeCheckbox)

            val kotak = kotakCheckbox.isChecked
            val oval = ovalCheckbox.isChecked
            val bulat = bulatCheckbox.isChecked
            val costume = costumeCheckbox.isChecked

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
                            val specsticker = SpecSticker(
                                idPesanan,
                                userID,
                                idSticker,
                                harga,
                                namaProject,
                                namamerk,
                                keterangan,
                                panjang,
                                lebar,
                                imageUrl,
                                kotak,
                                oval,
                                bulat,
                                costume
                            )
                            database.child(userID!!).child("Pesanan").child(idPesanan)
                                .setValue(specsticker)
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
                                    kotakCheckbox.isChecked = false
                                    ovalCheckbox.isChecked = false
                                    bulatCheckbox.isChecked = false
                                    costumeCheckbox.isChecked = false
                                    binding.namaMerk.text?.clear()
                                    binding.Uploadketerangan.text?.clear()
                                    binding.panjang.text?.clear()
                                    binding.lebar.text?.clear()

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

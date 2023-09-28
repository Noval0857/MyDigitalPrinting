package umbjm.ft.inf.mydigitalprinting.pembayaran

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
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityPembayaranBinding
import umbjm.ft.inf.mydigitalprinting.keranjang.KeranjangItem
import umbjm.ft.inf.mydigitalprinting.pesanan.DataPesanan
import umbjm.ft.inf.mydigitalprinting.pesanan.PesananActivity

class PembayaranActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private var imageUri: Uri? = null
    private var totalHarga: Double = 0.0
    private lateinit var binding: ActivityPembayaranBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPembayaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageInput()
        bayar()
    }

    private fun imageInput() {
        binding.imageButton.setOnClickListener {
            resultLauncher.launch("image/*")
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
        binding.imageButton.setImageURI(uri)
    }

    private fun bayar() {
        binding.kirim.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            val userID = user?.uid // Mendapatkan ID pengguna saat ini

            val totalHarga = intent.getDoubleExtra("totalHarga", 0.0)
            val ttotalHarga = totalHarga.toString()

            val idKeranjang = intent.getStringExtra("idKeranjang")

            val storageRef = FirebaseStorage.getInstance().reference.child("Pembayaran")
                .child(System.currentTimeMillis().toString())

            imageUri?.let {
                storageRef.putFile(it).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        storageRef.downloadUrl.addOnSuccessListener { uri ->
                            val imageUrl = uri.toString()
                            database = FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                .getReference("User")
                            val idPembayaran = database.push().key!!
                            val pembayaran = Pembayaran(
                                idPembayaran,
                                idKeranjang,
                                userID,
                                ttotalHarga,
                                imageUrl
                            )
                            database.child(userID!!).child("Pembayaran").child(idPembayaran)
                                .setValue(pembayaran)
                                .addOnCompleteListener { databaseTask ->
                                    if (databaseTask.isSuccessful) {
                                        Toast.makeText(
                                            this,
                                            "Uploaded Successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
//                                        sendDataToPesanan()

                                        intent.putExtra("totalHarga", ttotalHarga)
                                        intent.putExtra("idKeranjang", idKeranjang)
                                        intent.putExtra("idPembayaran", idPembayaran)
                                        val intent = Intent(this, PesananActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                    } else {
                        Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

//    private fun sendDataToPesanan() {
//
//        val user = FirebaseAuth.getInstance().currentUser
//        val userID = user?.uid // Mendapatkan ID pengguna saat ini
//
//        val totalHarga = intent.getDoubleExtra("totalHarga", 0.0)
//        val ttotalHarga = totalHarga.toString()
//
//
//    }
}
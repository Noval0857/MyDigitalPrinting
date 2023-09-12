package umbjm.ft.inf.mydigitalprinting.pembayaran

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityPembayaranBinding

class PembayaranActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private var imageUri: Uri? = null
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
    ) {

        imageUri = it
        binding.imageButton.setImageURI(it)
    }

    private fun bayar() {
        binding.kirim.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            val userID = user?.uid // Mendapatkan ID pengguna saat ini

            val totalHarga = intent.getDoubleExtra("totalHarga", 0.0)
            val ttotalHarga = totalHarga.toString()

            val storageRef = FirebaseStorage.getInstance().reference.child("Pembayaran")
                .child(System.currentTimeMillis().toString())

            imageUri?.let {
                storageRef.putFile(it).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Jika pengunggahan berhasil, dapatkan URL gambar
                        storageRef.downloadUrl.addOnSuccessListener { uri ->
                            // Simpan URL gambar ke Firebase Realtime Database
                            val imageUrl = uri.toString()
                            database =
                                FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")
                            val idPembayaran = database.push().key!!
                            val pembayaran = Pembayaran(
                                idPembayaran,
                                userID,
                                ttotalHarga,
                                imageUrl
                            )
                            database.child(userID!!).child("Pembayaran").child(idPembayaran).setValue(pembayaran)
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
}
package umbjm.ft.inf.mydigitalprinting.admin.itemstiker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.admin.AdminActivity
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityItemIdcardUpdateBinding
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityItemStickerUpdateBinding

class ItemStickerUpdate : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private var imageUri: Uri? = null
    private lateinit var binding: ActivityItemStickerUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemStickerUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idSticker = intent.getStringExtra("idSticker")
        val nameSticker = intent.getStringExtra("nameSticker")
        val imageSticker = intent.getStringExtra("imageSticker")
        val harga = intent.getStringExtra("harga")

        binding.nameSticker.setText(nameSticker)
        binding.hargaSticker.setText(harga)

        if (imageSticker != null) {
            // Tampilkan gambar menggunakan Picasso atau library lainnya
            Picasso.get().load(imageSticker).into(binding.imageSticker)
        } else {
            // Handle jika tidak ada gambar
        }

        imageInput()
        update(idSticker)
    }

    private fun imageInput() {
        binding.imageSticker.setOnClickListener {
            resultLauncher.launch("image/*")
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        imageUri = it
        binding.imageSticker.setImageURI(it)
    }

    private fun update(idSticker: String?) {
        binding.update.setOnClickListener {
            val nameSicker = binding.nameSticker.text.toString()
            val harga = binding.hargaSticker.text.toString()

            database =
                FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference("Products")
                    .child("Sticker")

            if (idSticker != null) {
                val updateSticker = HashMap<String, Any>()
                updateSticker["nameSticker"] = nameSicker
                updateSticker["harga"] = harga

                database.child(idSticker).updateChildren(updateSticker)
                    .addOnCompleteListener { databaseTask ->
                        if (databaseTask.isSuccessful) {
                            Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                        }
                    }
                imageUri?.let { uri ->
                    val storageRef = FirebaseStorage.getInstance().reference.child("Products")
                        .child("Idcard")

                    storageRef.putFile(uri).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Dapatkan URL gambar yang baru diunggah
                            storageRef.downloadUrl.addOnSuccessListener { newImageUrl ->
                                // Simpan URL gambar ke Firebase Realtime Database
                                val imageUrlUpdates = HashMap<String, Any>()
                                imageUrlUpdates["imageSticker"] = newImageUrl.toString()
                                database.child(idSticker).updateChildren(imageUrlUpdates)
                            }
                        } else {
                            Toast.makeText(this, "Gagal mengunggah gambar", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
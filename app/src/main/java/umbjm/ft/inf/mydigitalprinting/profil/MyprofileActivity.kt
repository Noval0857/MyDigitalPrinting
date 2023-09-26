package umbjm.ft.inf.mydigitalprinting.profil

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityMyProfileBinding
import umbjm.ft.inf.mydigitalprinting.produk.spesifikasi.SpecDesain

class MyprofileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyProfileBinding
    private lateinit var database: DatabaseReference
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backprofil.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        imageInput()
        upload()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val userID = user.uid // Mendapatkan ID pengguna saat ini
            getData(userID)



        } else {
            // Handle jika pengguna belum login
            // Contoh: Redirect pengguna ke halaman login
            // atau tampilkan pesan bahwa pengguna harus login terlebih dahulu
        }

        binding.SaveProfil.setOnClickListener {
            saveProfileChanges()
        }
    }

    private fun getData(userID: String) {
        database = FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("User")
        database.child(userID).child("Profil").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val nama = snapshot.child("nama").getValue(String::class.java)
                    val phone = snapshot.child("phone").getValue(String::class.java)
                    val image = snapshot.child("image").getValue(String::class.java)

                    if (image != null) {
                        // Tampilkan gambar menggunakan Picasso atau library lainnya
                        Picasso.get().load(image).into(binding.imageView)
                    } else {
                        // Handle jika tidak ada gambar
                    }

                    // Tampilkan data di tampilan
                    binding.NamaUserEdit.setText(nama)
                    binding.PhoneEdit.setText(phone)

                    // Tampilkan gambar profil
                    // Anda dapat menggunakan library seperti Picasso atau Glide untuk mengambil gambar dari URL
                    // Contoh dengan Picasso:
                    // Picasso.get().load(imageUrl).into(binding.imageView)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle kesalahan
            }
        })
    }

    private fun imageInput() {
        binding.imageView.setOnClickListener {
            resultLauncher.launch("image/*")
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {

        imageUri = it
        binding.imageView.setImageURI(it)
    }


    private fun upload() {
        binding.SaveProfil.setOnClickListener {

            val user = FirebaseAuth.getInstance().currentUser
            val userID = user?.uid // Mendapatkan ID pengguna saat ini



            val nama = binding.NamaUserEdit.text.toString()
            val phone = binding.PhoneEdit.text.toString()


            // Referensi Firebase Storage
            val storageRef = FirebaseStorage.getInstance().reference.child("User")
                .child(System.currentTimeMillis().toString())

            // Mengunggah gambar ke Firebase Storage
            imageUri?.let {
                storageRef.putFile(it).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Jika pengunggahan berhasil, dapatkan URL gambar
                        storageRef.downloadUrl.addOnSuccessListener { uri ->
                            // Simpan URL gambar ke Firebase Realtime Database
                            val imageUrl = uri.toString()
                            database =
                                FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")
                            val profil = ProfilUser(
                                userID, nama, phone, imageUrl
                            )
                            database.child(userID!!).child("Profil").setValue(profil)
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
                                    binding.NamaUserEdit.text?.clear()
                                    binding.PhoneEdit.text?.clear()


                                }
                        }
                    } else {
                        Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun saveProfileChanges() {
        val user = FirebaseAuth.getInstance().currentUser
        val userID = user?.uid

        val nama = binding.NamaUserEdit.text.toString()
        val phone = binding.PhoneEdit.text.toString()

        // Pastikan data yang diisi tidak kosong
        if (nama.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Nama dan nomor telepon harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        // Referensi Firebase Realtime Database untuk data pengguna
        database = FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("User")
            .child(userID!!)
            .child("Profil")

        // Simpan perubahan nama dan nomor telepon ke Firebase Realtime Database
        val profilUpdates = HashMap<String, Any>()
        profilUpdates["nama"] = nama
        profilUpdates["phone"] = phone
        database.updateChildren(profilUpdates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Perubahan berhasil disimpan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Gagal menyimpan perubahan", Toast.LENGTH_SHORT).show()
            }
        }

        // Referensi Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("User")
            .child(System.currentTimeMillis().toString())

        // Jika ada gambar yang dipilih, unggah gambar baru ke Firebase Storage
        imageUri?.let { uri ->
            storageRef.putFile(uri).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Dapatkan URL gambar yang baru diunggah
                    storageRef.downloadUrl.addOnSuccessListener { newImageUrl ->
                        // Simpan URL gambar ke Firebase Realtime Database
                        val imageUrlUpdates = HashMap<String, Any>()
                        imageUrlUpdates["image"] = newImageUrl.toString()
                        database.updateChildren(imageUrlUpdates)
                    }
                } else {
                    Toast.makeText(this, "Gagal mengunggah gambar", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
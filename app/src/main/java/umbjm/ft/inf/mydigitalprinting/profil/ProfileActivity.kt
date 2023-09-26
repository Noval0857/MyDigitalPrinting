package umbjm.ft.inf.mydigitalprinting.profil

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import umbjm.ft.inf.mydigitalprinting.MainActivity
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityProfilBinding
import umbjm.ft.inf.mydigitalprinting.login.ChangepasswordActivity
import umbjm.ft.inf.mydigitalprinting.login.LoginActivity
import umbjm.ft.inf.mydigitalprinting.preferen.SharedPreferences

class ProfileActivity : AppCompatActivity() {

    // Untuk mengakses Library
    lateinit var binding: ActivityProfilBinding
    lateinit var auth: FirebaseAuth
    lateinit var shp : SharedPreferences
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProfilBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Untuk pindah ke halaman AboutUS
        binding.aboutUs.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }

        // Untuk pindah halaman ke ContactActivity
        binding.ContactUs.setOnClickListener {
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }

        // Untuk pindah halaman ke MyProfile
        binding.imageUser.setOnClickListener {
            val intent = Intent(this, MyprofileActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }

        // Untuk kembali ke halaman Home
        binding.backprofil.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

        shp = SharedPreferences(this)

        // Untuk pindah ke halaman change password
        binding.changepassword.setOnClickListener {
            val intent = Intent(this, ChangepasswordActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }

        // Memberikan Aksi ke btnkeluar
        binding.btnKeluar.setOnClickListener {
            // Mengambil fungsi btnlogout dan menampungnya ke btnkeluar
            btnLogout()
        }

        if (intent.getBooleanExtra("logout", false)){
            finish()
        }

        // Pindah halaman ke MyProfileActivity
        binding.myprofil.setOnClickListener {
            val intent = Intent(this, MyprofileActivity::class.java)
            startActivity(intent)
            return@setOnClickListener
        }

        getUser()
    }

    // mengambil data yang ada pada change password
    override fun onResume() {
        super.onResume()

        // menampung data kedalam variable
        val pesan = intent.getStringExtra("data_masuk")
        // jika sukses maka akan menampilkan sebuah alert
        if (pesan == "success"){
            // ini alert yang akan ditampilkan
            SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setContentText("Berhasil update password")
                .show()
            // data akan diremove jika sudah berhasil, agar dapat diakases ulang
            intent.removeExtra("data_masuk")
        }
    }

    // Memberikan fungsi ke btnlogout
    private fun btnLogout() {
        // mengambil Authentikasi di firebaseAuth
        auth = FirebaseAuth.getInstance()
        // Alert
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            .setTitleText("Konfirmasi Logout")
            .setContentText("Apakah anda yakin ingin logout?")
            .setConfirmText("Ya")
            .setCancelText("Tidak")
            .setConfirmClickListener { dialog ->
                dialog.dismissWithAnimation()
                // Data penggunakan Logout dari database
                auth.signOut()

                // shp.put(Constant.PREF_LOGIN,false)

                shp.clear()

                // Alert Berhasil keluar
                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setContentText("Berhasil Logout")
                    .setConfirmText("Ok")
                    .setConfirmClickListener { SuccessDialog ->
                        SuccessDialog.dismissWithAnimation()
                        moveIntent()
                    }
                    .show()
            }
            // Alert tidak jadi keluar
            .setCancelClickListener { dialog ->
                dialog.dismissWithAnimation()
                val root = findViewById<View>(android.R.id.content)
                val snackbar = Snackbar.make(root, "Anda tidak logout", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
            .show()
    }

    private fun moveIntent() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finishAffinity()
    }

    private fun getUser(){
        val user = FirebaseAuth.getInstance().currentUser
        val userID = user?.uid
        database =
            FirebaseDatabase.getInstance("https://mydigitalprinting-60323-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("User")
        database.child(userID!!).child("Profil").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userData = snapshot.getValue(ProfilUser::class.java)
                    val username = userData?.nama
                    val image = userData?.image

                    val usernameTextView = findViewById<TextView>(R.id.Username)
                    usernameTextView.text = username



                    if (image != null && image.isNotEmpty()) {
                        // Tampilkan gambar menggunakan Picasso atau library lainnya
                        Picasso.get()
                            .load(image)
                            .placeholder(R.drawable.person)
                            .error(R.drawable.person)
                            .into(binding.imageUser)
                    } else {
                        // Handle jika tidak ada gambar
                        binding.imageUser.visibility = View.GONE
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}
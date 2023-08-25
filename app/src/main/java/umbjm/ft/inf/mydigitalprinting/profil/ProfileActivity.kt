package umbjm.ft.inf.mydigitalprinting.profil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import umbjm.ft.inf.mydigitalprinting.R
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityProfilBinding
import umbjm.ft.inf.mydigitalprinting.login.LoginActivity

class ProfileActivity : AppCompatActivity() {

    // Untuk mengakses Library
    lateinit var binding : ActivityProfilBinding
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProfilBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Memberikan Aksi ke btnkeluar
        binding.btnKeluar.setOnClickListener {
            // Mengambil fungsi btnlogout dan menampungnya ke btnkeluar
            btnLogout()
        }
    }

    // Memberikan fungsi ke btnlogout
    private fun btnLogout() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
        Toast.makeText(this, "Anda berhasil Log Out", Toast.LENGTH_SHORT).show()
    }
}
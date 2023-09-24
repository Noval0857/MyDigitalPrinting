package umbjm.ft.inf.mydigitalprinting.profil

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import umbjm.ft.inf.mydigitalprinting.databinding.ActivityMyProfileBinding

class MyprofileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backprofil.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}